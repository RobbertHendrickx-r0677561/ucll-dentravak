package be.ucll.da.dentravak.controller;

import be.ucll.da.dentravak.model.db.OrderRepository;
import be.ucll.da.dentravak.model.db.SandwichRepository;
import be.ucll.da.dentravak.model.domain.Bread;
import be.ucll.da.dentravak.model.domain.Orders;
import be.ucll.da.dentravak.model.domain.Sandwich;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
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
            Orders o1 = new Orders("049581245", s2.getId(), Bread.Botterhammekes);
            Orders o2 = new Orders("049581245", s.getId(), Bread.TurkishBread);
            orderService.save(o1);
            orderService.save(o2);
        }

        @GetMapping("/sandwiches")
        public Iterable<Sandwich> getSandwiches() {
            return sandwichService.findAll();
        }

        @GetMapping("/sandwiches/{id}")
        public Optional<Sandwich> getSandwich(@PathVariable UUID id) {
        return sandwichService.findById(id);
        }

        @PutMapping("/sandwiches/{id}")
        public Sandwich putSandwich(@PathVariable UUID id, @RequestBody Sandwich newSandwich){
            Sandwich s2 = new Sandwich();
            for (Sandwich s : sandwichService.findAll()){
                if(s.getId().equals(id)) {
                    //s.setId(UUID.randomUUID());
                    s.setName(newSandwich.getName());
                    s.setPrice(newSandwich.getPrice());
                    s.setIngredients(newSandwich.getIngredients());
                    s2 = s;
                    return sandwichService.save(s);
                }
            }
            return s2;
        }

        @PostMapping("/sandwiches")
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

        @GetMapping("/orders")
        public Iterable<Orders> getOrderByDate(@RequestParam("date") Date date){
            //@RequestParam(value = "date", required = false) Date dateOrNull);
            return orderService.findByDate(date);
        }

        @GetMapping("/order")
        public Iterable<Orders> getOrder(){
            return orderService.findAll();
        }

        @PostMapping("/orders")
        public Orders postOrder(@RequestBody Orders newOrder){
            Orders o = newOrder;
            Optional<Sandwich> s = sandwichService.findById(newOrder.getSandwichId());
            o.setName(s.get().getName());
            o.setPrice(s.get().getPrice());
        return orderService.save(newOrder);
    }


}
