package src.model;

import src.ast.arugments.Product;

import java.util.HashMap;

public class Inventory extends HashMap<Product, Integer> {

    public Inventory() {

    }

    public Inventory(Inventory duplicationInventory) {
        super(duplicationInventory);
    }
}
