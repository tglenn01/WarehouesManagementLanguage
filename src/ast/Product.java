package src.ast;

import src.ast.Node;
import src.ast.locations.Shelf;

import java.util.ArrayList;
import java.util.List;

// An item that has a name and assigned shelf
public class Product extends Node {
    public static final String[] VALID_PRODUCTS = {
            "Apple",
            "Banana",
            "Pear",
            "Grape"
    };





    private String name;
    private Integer shelfLocation;

    public Product(String name, Integer shelfLocation) {
        this.name = name;
        this.shelfLocation = shelfLocation;
    }

    /**
     *
     * Updates the shelf at which the product is stored, removes this product from the old shelf, so it cannot be in
     * two shelves at once
     *
     * @param shelfLocation: The new shelf location
     *
     */
    public void updateShelfLocation(Integer shelfLocation) {
        // stub
    }

    // return the shelf location that the products is stored at
    public Integer getProductShelfLocation() {
        return shelfLocation;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
