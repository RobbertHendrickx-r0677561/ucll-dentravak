package be.ucll.da.dentravak.model.db;

import be.ucll.da.dentravak.model.domain.Sandwich;

import java.util.List;

public interface SandwichDB {

    Sandwich getSandwich(String name);
    List<Sandwich> getSandwiches();
    void addSandwich(Sandwich sandwich);
    void updateSandwich(Sandwich sandwich);
    void deleteSandwich(String name);
}