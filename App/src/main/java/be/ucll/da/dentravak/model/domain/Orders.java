package be.ucll.da.dentravak.model.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Orders {

    @Id
    @GeneratedValue
    private UUID orderId;
    private String gsm;
    private UUID sandwichId;
    private Bread bread;
    private String name;
    private double price;
    private LocalDateTime date;


    public Orders(){
        setDate();
    }

    public Orders(String gsm, UUID sandwich, Bread bread) {
        setOrderId(orderId);
        setGsm(gsm);
        setBread(bread);
        setSandwichId(sandwich);
        setDate();
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public Bread getBread() {
        return bread;
    }

    public void setBread(Bread bread) {
        this.bread = bread;
    }

    public UUID getSandwichId() {
        return sandwichId;
    }

    public void setSandwichId(UUID sandwichId) {
        this.sandwichId = sandwichId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate() {
        this.date = LocalDateTime.now();
    }
}
