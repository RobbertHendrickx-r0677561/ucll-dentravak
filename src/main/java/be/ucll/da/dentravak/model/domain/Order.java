package be.ucll.da.dentravak.model.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Order {

    @Id
    @GeneratedValue
    private UUID orderId;
    private String gsm;
    private Sandwich sandwich;
    private Bread bread;

    public Order(){}
    public Order(String gsm, Sandwich sandwich, Bread bread) {
        this.gsm = gsm;
        this.sandwich = sandwich;
        this.bread = bread;
        //this.orderId = orderId;
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    public Sandwich getSandwich() {
        return sandwich;
    }

    public void setSandwich(Sandwich sandwich) {
        this.sandwich = sandwich;
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
}
