package be.ucll.da.dentravak.controller;

import be.ucll.da.dentravak.model.db.SandwichRepository;
import be.ucll.da.dentravak.model.domain.Bread;
import be.ucll.da.dentravak.model.domain.Order;
import be.ucll.da.dentravak.model.domain.Sandwich;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShopController {

        SandwichRepository serv ;

        ShopController(SandwichRepository repository){
            this.serv = repository;
            Sandwich s = new Sandwich("smos", 3.6, "hesp, kaas, groentjes");
            serv.save(s);
        }
        @GetMapping("/sandwich")
        public Iterable<Sandwich> getSandwich() {
            return serv.findAll();
        }

        @GetMapping("/order")
        public Order getOrder(){
            Sandwich s = new Sandwich("smos", 3.6, "hesp, kaas, groentjes");
            Order o = new Order("049581245", s, Bread.TurkishBread);
            return o;
        }
        @PostMapping("/postSandwich")
        public Sandwich postSandwich(@RequestBody Sandwich newSandwich, @PathVariable String name){
            for (Sandwich s : serv.findAll()){
                if(s.getName().equals(name)) {
                    s.setName(newSandwich.getName());
                    s.setPrice(newSandwich.getPrice());
                    s.setIngredients(newSandwich.getIngredients());
                    return serv.save(s);
                }
            }
            newSandwich.setName(name);
            return serv.save(newSandwich);
        }
}
