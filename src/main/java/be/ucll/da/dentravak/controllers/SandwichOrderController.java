package be.ucll.da.dentravak.controllers;

import be.ucll.da.dentravak.model.Sandwich;
import be.ucll.da.dentravak.model.SandwichOrder;
import be.ucll.da.dentravak.repositories.SandwichOrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@RestController
public class SandwichOrderController {

    private SandwichOrderRepository repository;

    public SandwichOrderController(SandwichOrderRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public Iterable<SandwichOrder> orders() {
        return repository.findAll();
    }

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public SandwichOrder createSandwichOrder(@RequestBody SandwichOrder sandwichOrder) {
        sandwichOrder.setCreationDate(LocalDateTime.now());
        return repository.save(sandwichOrder);
    }

    @RequestMapping(value = "/download-orders.csv", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public FileSystemResource ordersDownload(HttpServletResponse response) throws IOException {
        PrintWriter pw = new PrintWriter("download-orders.csv");
        StringBuilder sb = new StringBuilder();
        sb.append("id");
        sb.append(',');
        sb.append("sandwichId");
        sb.append(',');
        sb.append("name");
        sb.append(',');
        sb.append("breadType");
        sb.append(',');
        sb.append("creationDate");
        sb.append(',');
        sb.append("price");
        sb.append(',');
        sb.append("mobilePhoneNumber");
        sb.append('\n');

        for(SandwichOrder order : repository.findAll()){
            sb.append(order.getId());
            sb.append(',');
            sb.append(order.getSandwichId());
            sb.append(',');
            sb.append(order.getName());
            sb.append(',');
            sb.append(order.getBreadType().name());
            sb.append(',');
            sb.append(order.getCreationDate());
            sb.append(',');
            sb.append(order.getPrice());
            sb.append(',');
            sb.append(order.getMobilePhoneNumber());
            sb.append('\n');
        }
        pw.write(sb.toString());
        return new FileSystemResource("download-orders.csv");
    }

}

