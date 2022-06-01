package src.model;

import exceptions.NoSuchProductException;
import src.ast.arugments.Name;
import src.ast.arugments.Product;
import src.ast.arugments.locations.Shelf;

import java.util.Arrays;
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

    public static Product getProductGivenName(Name name) throws NoSuchProductException {
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

        if (Arrays.stream(VALID_PRODUCTS).toList().contains(name.name)) {
            return new Product(name, 0);
        }

        throw new NoSuchProductException(name.name);
    }
}
