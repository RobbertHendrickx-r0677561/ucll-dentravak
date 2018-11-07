package be.ucll.da.dentravak.model.domain;

import java.util.List;
import java.util.UUID;

public class Sandwich {
    private UUID id;
    private String name;
    private double price;
    private List<Ingredient> ingredients;
    private boolean vegetarian;


    public Sandwich(String name, double price, List<Ingredient> ingredients) {
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
        setVegetarian();
    }

    public boolean isVegetarian(){
        for (Ingredient ing: ingredients) {
            if(!ing.getVegetarian()){
                return false;
            }
        }
        return true;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void addIngredient(Ingredient ing){
        ingredients.add(ing);
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setVegetarian() {
        if(isVegetarian()){
            this.vegetarian = true;
        }
        this.vegetarian = false;
    }
}
