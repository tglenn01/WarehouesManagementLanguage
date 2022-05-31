package src.ast;

import exceptions.ProductNotValidOnShelfException;
import src.ast.locations.Shelf;
import src.model.InventoryManager;
import src.model.Warehouse;

import java.util.Objects;

// An item that has a name and assigned shelf
public class Product extends Argument {

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
     * @param newShelfLocation: The new shelf location
     *
     */
    public void updateShelfLocation(Integer newShelfLocation) {
        Warehouse warehouse = InventoryManager.warehouse;
        Shelf oldLocation =  warehouse.getShelfAtLocation(this.shelfLocation);
        Shelf newLocation = warehouse.getShelfAtLocation(newShelfLocation);

        try {
            oldLocation.removeProductFromShelf(this);
        } catch (ProductNotValidOnShelfException e) {
            // ignore since it already isn't there
        }

        this.shelfLocation = newShelfLocation;
        newLocation.addProductToShelf(this);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(shelfLocation, product.shelfLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, shelfLocation);
    }
}
