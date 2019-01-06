package be.ucll.da.dentravak.controllers;

import be.ucll.da.dentravak.model.SandwichOrder;
import be.ucll.da.dentravak.repositories.SandwichOrderRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
    public void ordersDownload(HttpServletResponse response) throws IOException {
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
        sb.append(',');
        sb.append("printed");
        sb.append('\n');

        for(SandwichOrder order : repository.findAll()){
            if(order.getCreationDate().getDayOfYear() == LocalDateTime.now().getDayOfYear() && !order.isPrinted()){
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
                sb.append(',');
                sb.append(order.isPrinted());
                sb.append('\n');
                order.setPrinted(true);
                repository.save(order);
            }
        }

        response.setContentType("text/csv");
        InputStream inputStream = new ByteArrayInputStream(sb.toString().getBytes());
        IOUtils.copy(inputStream, response.getOutputStream());
        response.flushBuffer();
    }

}

