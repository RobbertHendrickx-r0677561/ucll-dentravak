package be.ucll.da.dentravak.controllers;

import be.ucll.da.dentravak.model.Sandwich;
import be.ucll.da.dentravak.model.SandwichPreferences;
import be.ucll.da.dentravak.repositories.SandwichRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.naming.ServiceUnavailableException;
import java.net.URI;
import java.util.*;

import static org.hibernate.internal.util.collections.ArrayHelper.toList;

@RestController
public class SandwichController {

    @Inject
    private DiscoveryClient discoveryClient;

    @Inject
    private SandwichRepository repository;

    @Inject
    private RestTemplate restTemplate;

    @RequestMapping("/sandwiches")
    public Iterable<Sandwich> sandwiches() {

        try {
            SandwichPreferences preferences = getPreferences("0412345678");
            Iterable<Sandwich> allSandwiches = repository.findAll();
            List<Sandwich> sandwiches = new ArrayList<>();
            for (Sandwich s : allSandwiches) {
                sandwiches.add(s);
            };

            Collections.sort(sandwiches, new Comparator<Sandwich>() {
                @Override
                public int compare(Sandwich s1, Sandwich s2)
                {
                    Float rating1 = preferences.getRatingForSandwich(s1.getId());
                    Float rating2 = preferences.getRatingForSandwich(s2.getId());
                    if (rating1 == null) {
                        rating1 = new Float(0.00);
                    }
                    if (rating2 == null) {
                        rating2 = new Float(0.00);
                    }
                    //return rating1.compareTo(rating2);
                    return (int) (rating1 - rating2);
                }
            });

            Collections.reverse(sandwiches);
            return sandwiches;

        } catch (ServiceUnavailableException e) {
            return repository.findAll();
        }

    }


    @RequestMapping(value = "/sandwiches", method = RequestMethod.POST)
    public Sandwich createSandwich(@RequestBody Sandwich sandwich) {
        return repository.save(sandwich);
    }

    @RequestMapping("/sandwiches/{id}")
    public Optional<Sandwich> getSandwich(@PathVariable UUID id) {
        return repository.findById(id);
    }

    @RequestMapping(value = "/sandwiches/{id}", method = RequestMethod.PUT)
    public Sandwich updateSandwich(@PathVariable UUID id, @RequestBody Sandwich sandwich) {
        if(!(sandwich.getId().equals(id))) throw new IllegalArgumentException("trying to hack?");
        return repository.save(sandwich);
    }

    @GetMapping("/getpreferences/{emailAddress}")
    public SandwichPreferences getPreferences(@PathVariable String emailAddress) throws RestClientException, ServiceUnavailableException {
        URI service = recommendationServiceUrl()
                .map(s -> s.resolve("/recommendation/recommend/" + emailAddress))
                .orElseThrow(ServiceUnavailableException::new);
        return restTemplate
                .getForEntity(service, SandwichPreferences.class)
                .getBody();
    }

    public Optional<URI> recommendationServiceUrl() {
       return discoveryClient.getInstances("recommendation")
                .stream()
                .map(si -> si.getUri())
                .findFirst();
    }
}

