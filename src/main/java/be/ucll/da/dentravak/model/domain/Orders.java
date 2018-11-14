package be.ucll.da.dentravak.model.domain;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Orders {

    @Id
    @GeneratedValue
    private UUID orderId;
    private String gsm;
    private UUID sandwichId;
    private Bread bread;

    public Orders(){}

    public Orders(String gsm, UUID sandwich, Bread bread) {
        setOrderId(orderId);
        setGsm(gsm);
        setBread(bread);
        setSandwichId(sandwich);
        //this.orderId = orderId;
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

}
