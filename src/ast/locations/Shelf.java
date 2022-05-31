package src.ast.locations;

import exceptions.InsufficientProductsException;
import exceptions.ProductNotValidOnShelfException;
import src.ast.Argument;
import src.ast.Product;
import src.ast.WarehouseRobotVisitor;

import java.util.*;

// A shelf with an amount of products that are stored there
public class Shelf extends Argument implements Location {
    // TODO: implement a max quantity of products in a shelf
    // public final static int MAX_QUANTITY_OF_PRODUCTS_IN_SHELF = 10;

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
     * @throws ProductNotValidOnShelfException : If the product is trying to be added to the incorrect shelf
     *
     */
    public void restockProduct(Product product, Integer amount) throws ProductNotValidOnShelfException {
        if (!validProducts.contains(product)) {
            throw new ProductNotValidOnShelfException(product, this.warehouseLocation);
        }

        int newAmount;

        if (inventory.containsKey(product)) {
            newAmount = inventory.get(product) + amount;
        } else {
            newAmount = amount;
        }

        inventory.put(product, newAmount);
    }

    /**
     *
     * Picks up the given product from the shelf
     *
     * @param product: Product to be picked up from the shelf
     * @param amount: amount of product to be picked up
     *
     * @throws InsufficientProductsException: If there is not enough of the product on the shelf
     * @return returns the map with the product and the amount given
     *
     */
    public Map<Product, Integer> pickUpProduct(Product product, Integer amount) throws InsufficientProductsException, ProductNotValidOnShelfException {
        if (!validProducts.contains(product)) {
            throw new ProductNotValidOnShelfException(product, this.warehouseLocation);
        }

        if (!inventory.containsKey(product) || (inventory.get(product) < amount)) {
            throw new InsufficientProductsException();
        }

        Integer currentAmountOfProduct = inventory.get(product);
        int newAmountOfProduct = currentAmountOfProduct - amount;

        if (newAmountOfProduct == 0) {
            inventory.remove(product);
        } else {
            inventory.put(product, newAmountOfProduct);
        }

        Map<Product, Integer> returnVal = new HashMap<>();
        returnVal.put(product, amount);

        return returnVal;
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

        if (product.getProductShelfLocation() != this.warehouseLocation) {
            product.updateShelfLocation(this.warehouseLocation);
        }

    }

    /**
     *
     * Removes the product as a valid type of the shelf, updates products to have that as their new location
     *
     * @param product: product to be removes to the shelf
     *
     */
    public void removeProductFromShelf(Product product) throws ProductNotValidOnShelfException {
        if (!validProducts.contains(product)) {
            throw new ProductNotValidOnShelfException(product, this.warehouseLocation);
        }

        if (inventory.containsKey(product)) {
            inventory.remove(product);
        }

        validProducts.remove(product);
    }

    // returns if the product is a part of the valid products
    public boolean isProductValid(Product product) {
        return validProducts.contains(product);
    }

    public boolean isProductValidGivenName(String name) {
        for (Product product : validProducts) {
            if (product.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasEnoughProduct(Product product, Integer amountNeeded) throws ProductNotValidOnShelfException {
        if (!validProducts.contains(product)) {
            throw new ProductNotValidOnShelfException(product, this.warehouseLocation);
        }

        if (!inventory.containsKey(product)) {
            return false;
        }

        return inventory.get(product) >= amountNeeded;
    }

    public Integer getAmountOfProductLeft(Product product) throws ProductNotValidOnShelfException {
        if (!validProducts.contains(product)) {
            throw new ProductNotValidOnShelfException(product, this.warehouseLocation);
        }

        if (!inventory.containsKey(product)) {
            return 0;
        }

        return inventory.get(product);
    }

    public String getLocationName() {
        return "Shelf: " + warehouseLocation;
    }

    public Map<Product, Integer> getInventoryData() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelf shelf = (Shelf) o;
        return warehouseLocation == shelf.warehouseLocation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(warehouseLocation);
    }
}
