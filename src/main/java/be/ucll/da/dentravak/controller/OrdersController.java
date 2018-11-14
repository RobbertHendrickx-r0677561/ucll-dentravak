
package be.ucll.da.dentravak.controller;

import be.ucll.da.dentravak.model.db.OrderRepository;
import be.ucll.da.dentravak.model.domain.Bread;
import be.ucll.da.dentravak.model.domain.Orders;
import be.ucll.da.dentravak.model.domain.Sandwich;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrdersController {

    private OrderRepository orderService;

    @Autowired
    public OrdersController(OrderRepository orderService) {
        this.orderService = orderService;
        Sandwich s = new Sandwich("smos", 3.6, "hesp, kaas, groentjes");
        Orders o = new Orders("049581245", s.getId(), Bread.TurkishBread);
        orderService.save(o);
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
