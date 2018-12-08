package be.ucll.da.dentravak.controllers;

import be.ucll.da.dentravak.model.Sandwich;
import be.ucll.da.dentravak.repositories.SandwichRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@RestController
public class SandwichController {

    private SandwichRepository repository;

    public SandwichController(SandwichRepository repository) {
        this.repository = repository;
    }

    @RequestMapping("/sandwiches")
    public Iterable<Sandwich> sandwiches() {
        return repository.findAll();
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
}

