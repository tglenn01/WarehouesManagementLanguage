package src.ast.locations;

import exceptions.ProductNotOnShelfException;
import src.ast.Node;
import src.ast.Product;

import java.util.HashMap;
import java.util.Map;

// A shelf with an amount of products that are stored there
public class Shelf extends Node implements Location {
    public final static int MAX_QUANTITY_OF_PRODUCTS_IN_SHELF = 10;

    private Map<Product, Integer> products;
    private int xLocation;
    private int yLocation;

    public Shelf(Map<Product, Integer> initializedShelf) {
        products = initializedShelf;
    }

    public Shelf() {
        products = new HashMap<>();
    }


    /**
     *
     * Restocks the shelf with the given products
     *
     * @param product: Product to be added to shelf
     * @param amount: amount of product to be added to the shelf
     *
     * @throws ProductNotOnShelfException: If the product is trying to be added to the incorrect shelf
     *
     */
    public void restockProduct(Product product, Integer amount) throws ProductNotOnShelfException {
        //stub
    }


    /**
     *
     * Adds the product as a valid type of the shelf, updates products to have that as their new location
     *
     * @param product: product to be added to the shelf
     *
     */
    public void addProductToShelf(Product product) {
        // stub
    }
}
