package be.ucll.da.dentravak.controller;

//import be.ucll.da.dentravak.model.db.OrderRepository;
import be.ucll.da.dentravak.model.db.SandwichRepository;
import be.ucll.da.dentravak.model.domain.Bread;
import be.ucll.da.dentravak.model.domain.Order;
import be.ucll.da.dentravak.model.domain.Sandwich;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShopController {

        private SandwichRepository sandwichService ;
        //private OrderRepository orderService;

        public ShopController(SandwichRepository sandwichService){ //OrderRepository orderService
            this.sandwichService = sandwichService;
            //this.orderService = orderService;
            Sandwich s = new Sandwich("smos", 3.6, "hesp, kaas, groentjes");
            Order o = new Order("049581245", s, Bread.TurkishBread);
            sandwichService.save(s);
            //orderService.save(o);
        }
        @GetMapping("/sandwich")
        public Iterable<Sandwich> getSandwich() {
            return sandwichService.findAll();
        }
/*
        @GetMapping("/order")
        public Iterable<Order> getOrder(){
            return orderService.findAll();
        }

        @PostMapping("/postOrder")
        public Order postOrder(@RequestBody Order newOrder){
            return orderService.save(newOrder);
        }
        */
        @PostMapping("/postSandwich")
        public Sandwich postSandwich(@RequestBody Sandwich newSandwich){ //, @PathVariable String name
            /*
            for (Sandwich s : sandwichService.findAll()){
                if(s.getName().equals(name)) {
                    s.setName(newSandwich.getName());
                    s.setPrice(newSandwich.getPrice());
                    s.setIngredients(newSandwich.getIngredients());
                    return sandwichService.save(s);
                }
            }
            */
            //newSandwich.setName(name);
            return sandwichService.save(newSandwich);
        }
}
