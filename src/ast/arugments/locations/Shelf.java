package src.ast.arugments.locations;

import exceptions.InsufficientProductsException;
import exceptions.ProductNotValidOnShelfException;
import src.ast.WarehouseRobotVisitor;
import src.ast.arugments.Argument;
import src.ast.arugments.Name;
import src.ast.arugments.Num;
import src.ast.arugments.Product;
import src.model.Inventory;

import java.util.*;

// A shelf with an amount of products that are stored there
public class Shelf extends Argument implements Location {
    // TODO: implement a max quantity of products in a shelf
    // public final static int MAX_QUANTITY_OF_PRODUCTS_IN_SHELF = 10;

    private Inventory inventory;
    private List<Product> validProducts;
    private int warehouseLocation;

    public Shelf(Integer warehouseLocation, Inventory inventory, List<Product> validProducts) {
        this.warehouseLocation = warehouseLocation;
        this.inventory = inventory;
        this.validProducts = validProducts;
        this.nodeTitle = "Shelf";
    }

    public Shelf(Integer warehouseLocation, Inventory inventory) {
        this.warehouseLocation = warehouseLocation;
        this.inventory = inventory;
        this.validProducts = new ArrayList<>();
        this.nodeTitle = "Shelf";
    }

    public Shelf(Integer warehouseLocation, List<Product> validProducts) {
        this.warehouseLocation = warehouseLocation;
        this.inventory = new Inventory();
        this.validProducts = validProducts;
        this.nodeTitle = "Shelf";
    }

    public Shelf(Integer warehouseLocation) {
        this.warehouseLocation = warehouseLocation;
        this.inventory = new Inventory();
        this.validProducts = new ArrayList<>();
        this.nodeTitle = "Shelf";
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
    public void restockProduct(Product product, Num amount) throws ProductNotValidOnShelfException {

        if (!validProducts.contains(product)) {
            throw new ProductNotValidOnShelfException(product, this.warehouseLocation);
        }

        Num newAmount;

        if (inventory.containsKey(product)) {
            newAmount = Num.add(inventory.get(product), amount);
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
    public Inventory pickUpProduct(Product product, Num amount) throws InsufficientProductsException, ProductNotValidOnShelfException {
        if (!validProducts.contains(product)) {
            throw new ProductNotValidOnShelfException(product, this.warehouseLocation);
        }

        if (!inventory.containsKey(product) || (inventory.get(product).number < amount.number)) {
            throw new InsufficientProductsException();
        }

        Num currentAmountOfProduct = inventory.get(product);
        Num newAmountOfProduct = Num.subtract(currentAmountOfProduct, amount);

        if (newAmountOfProduct.number == 0) {
            inventory.remove(product);
        } else {
            inventory.put(product, newAmountOfProduct);
        }

        Inventory returnVal = new Inventory();
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

    public boolean isProductValidGivenName(Name name) {
        for (Product product : validProducts) {
            String productName = product.getName().name;
            String stringName = name.name;

            if (productName.equals(stringName)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasEnoughProduct(Product product, Num amountNeeded) throws ProductNotValidOnShelfException {
        if (!validProducts.contains(product)) {
            throw new ProductNotValidOnShelfException(product, this.warehouseLocation);
        }

        if (!inventory.containsKey(product)) {
            return false;
        }

        Integer currentAmount = inventory.get(product).number;

        return currentAmount >= amountNeeded.number;
    }

    public Num getAmountOfProductLeft(Product product) throws ProductNotValidOnShelfException {
        if (!validProducts.contains(product)) {
            throw new ProductNotValidOnShelfException(product, this.warehouseLocation);
        }

        if (!inventory.containsKey(product)) {
            return new Num(0);
        }

        return inventory.get(product);
    }

    public String getLocationName() {
        return "Shelf: " + warehouseLocation;
    }

    public Inventory getInventoryData() {
        return new Inventory(inventory);
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
