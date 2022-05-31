package src.model;

import exceptions.NoSuchProductException;
import src.ast.Product;
import src.ast.locations.Shelf;

import java.util.Map;

public class ProductMasterList {
    public static final String[] VALID_PRODUCTS = {
            "Apple",
            "Banana",
            "Pear",
            "Grape",
            "Pomegranate",
            "Watermelon",
            "Cherry",
            "Peach",
            "Milk",
            "Egg",
            "Pork",
            "Beef",
            "Chicken",
            "Granola",
            "Tea",
            "Coffee",
            "Pizza",
            "Soda",
            "Carrot",
            "Pepper",
            "Water",
            "Bagel",
            "Bread",
            "Cheese",
            "Fries",
            "Lemon",
            "Orange"};

    public static Product getProductGivenName(String name) throws NoSuchProductException {
        Warehouse warehouse = InventoryManager.warehouse;
        Map<Integer, Shelf> shelfData = warehouse.getShelvesData();

        for (Shelf shelf : shelfData.values()) {
            if (shelf.isProductValidGivenName(name)) {
                for (Product product : shelf.getInventoryData().keySet()) {
                    if (product.getName().equals(name)) {
                        return product;
                    }
                }
            }
        }

        throw new NoSuchProductException(name);
    }
}
