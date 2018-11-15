package be.ucll.da.dentravak.controller;

import be.ucll.da.dentravak.model.db.OrderRepository;
import be.ucll.da.dentravak.model.db.SandwichRepository;
import be.ucll.da.dentravak.model.domain.Bread;
import be.ucll.da.dentravak.model.domain.Orders;
import be.ucll.da.dentravak.model.domain.Sandwich;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class SandwichController {

        private SandwichRepository sandwichService ;
        private OrderRepository orderService;

        @Autowired
        public SandwichController(SandwichRepository sandwichService, OrderRepository orderService) {
            this.sandwichService = sandwichService;
            Sandwich s = new Sandwich("smos", 3.6, "hesp, kaas, groentjes");
            Sandwich s1 = new Sandwich("americain", 4.0, "vlees, augurken, ajuin");
            Sandwich s2 = new Sandwich("boulet", 4.6, "boulet, andalous, groentjes");

            sandwichService.save(s);
            sandwichService.save(s1);
            sandwichService.save(s2);

            this.orderService = orderService;
            Orders o = new Orders("049581245", s.getId(), Bread.TurkishBread);
            orderService.save(o);
        }

        @GetMapping("/sandwich")
        public Iterable<Sandwich> getSandwich() {
            return sandwichService.findAll();
        }

        @PutMapping("/putSandwich")
        public Sandwich postSandwich(@RequestBody Sandwich newSandwich){ //, @PathVariable String name

            for (Sandwich s : sandwichService.findAll()){
                if(s.getName().equals(newSandwich.getName())) {
                    //s.setId(UUID.randomUUID());
                    s.setName(newSandwich.getName());
                    s.setPrice(newSandwich.getPrice());
                    s.setIngredients(newSandwich.getIngredients());
                    return sandwichService.save(s);
                }
            }

            //newSandwich.setName(name);
            return sandwichService.save(newSandwich);
        }

        @GetMapping("/order")
        public Iterable<Orders> getOrder(){
        return orderService.findAll();
    }

        @PostMapping("/postOrder")
        public Orders postOrder(@RequestBody Orders newOrder){
        return orderService.save(newOrder);
    }
}
