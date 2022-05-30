package src.ast.locations;

import exceptions.InsufficientProductsException;
import exceptions.ProductNotOnShelfException;
import src.ast.Node;
import src.ast.Product;
import src.ast.WarehouseRobotVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// A shelf with an amount of products that are stored there
public class Shelf extends Node implements Location {
    public final static int MAX_QUANTITY_OF_PRODUCTS_IN_SHELF = 10;

    private Map<Product, Integer> inventory;
    private List<Product> validProducts;
    private int warehouseLocation;

    public Shelf(Integer warehouseLocation, Map<Product, Integer> inventory, List<Product> validProducts) {
        this.warehouseLocation = warehouseLocation;
        this.inventory = inventory;
        this.validProducts = validProducts;
    }

    public Shelf(Integer warehouseLocation, Map<Product, Integer> inventory) {
        this.warehouseLocation = warehouseLocation;
        this.inventory = inventory;
        this.validProducts = new ArrayList<>();
    }

    public Shelf(Integer warehouseLocation, List<Product> validProducts) {
        this.warehouseLocation = warehouseLocation;
        this.inventory = new HashMap<>();
        this.validProducts = validProducts;
    }

    public Shelf(Integer warehouseLocation) {
        this.warehouseLocation = warehouseLocation;
        this.inventory = new HashMap<>();
        this.validProducts = new ArrayList<>();
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
        if (!validProducts.contains(product)) {
            throw new ProductNotOnShelfException();
        }

        if (inventory.containsKey(product)) {
            amount = inventory.get(product);
        }

        inventory.put(product, amount);
    }

    /**
     *
     * Picks up the given product from the shelf
     *
     * @param product: Product to be picked up from the shelf
     * @param amount: amount of product to be picked up
     *
     * @throws InsufficientProductsException: If their is not enough of the product on the shelf
     * @return : returns the map with the product and the amount given
     *
     */
    public Map<Product, Integer> pickUpProduct(Product product, Integer amount) throws InsufficientProductsException {
        //stub
        return null;
    }

    /**
     *
     * Adds the product as a valid type of the shelf, updates products to have that as their new location
     *
     * @param product: product to be added to the shelf
     *
     */
    public void addProductToShelf(Product product) {
        if (!validProducts.contains(product)) {
            validProducts.add(product);
        }
    }

    /**
     *
     * Removes the product as a valid type of the shelf, updates products to have that as their new location
     *
     * @param product: product to be removes to the shelf
     *
     */
    public void removeProductFromShelf(Product product) {
        // stub
    }

    public Map<Product, Integer> getProductData() {
        return new HashMap<>(inventory);
    }

    public List<Product> getValidProductData() {
        return new ArrayList<>(validProducts);
    }

    public Integer getWarehouseLocation() {
        return this.warehouseLocation;
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
