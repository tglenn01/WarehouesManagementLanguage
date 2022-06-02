package src.model;

import src.ast.arugments.Num;
import src.ast.arugments.Product;

import java.util.HashMap;

public class Inventory extends HashMap<Product, Num> {

    public Inventory() {

    }

    public Inventory(Inventory duplicationInventory) {
        super(duplicationInventory);
    }
}
