package be.ucll.da.dentravak.model.domain;

import java.util.UUID;

public class Ingredient {

    private UUID id;
    private String name;
    private boolean vegetarian;

    public Ingredient(String naam, boolean vegetarian) {
        setName(naam);
        setVegetarian(vegetarian);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }
}
