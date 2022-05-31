package src.model;

import exceptions.InvalidLocationException;
import exceptions.ProductNotValidOnShelfException;
import src.ast.arugments.Product;
import src.ast.arugments.locations.Shelf;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// The warehouse where all products are stored in their respective shelves
public class Warehouse {
    private Map<Integer, Shelf> shelves;

    public Warehouse(Map<Integer, Shelf> filledShelves) {
        this.shelves = filledShelves;
    }

    public Warehouse() {
        shelves = null;
    }

    /**
     * Check if there is enough amount of the given product
     *
     * @param product: Product to check
     * @param amount:  Amount needed
     * @return true if available else false
     * @throws ProductNotValidOnShelfException: if the product we are asking for could not be found
     */
    public boolean checkAvailability(Product product, Integer amount) throws ProductNotValidOnShelfException {
        int shelfLocation = product.getProductShelfLocation();
        Shelf shelf = shelves.get(shelfLocation);

        return shelf.hasEnoughProduct(product, amount);
    }


    /**
     * Updates shelf at given location
     *
     * @param shelf:    shelf to be replaced
     * @param location: shelf location
     */
    public void updateShelf(Shelf shelf, Integer location) throws InvalidLocationException {
        if (!shelves.containsKey(location)) {
            throw new InvalidLocationException("Shelf at location " + location + " does not exist");
        }

        shelves.put(location, shelf);
    }


    public Shelf getShelfAtLocation(Integer location) {
        return shelves.get(location);
    }

    public boolean doesShelfLocationExist(Integer shelfLocation) {
        for (Shelf shelf : shelves.values()) {
            if (Objects.equals(shelf.getWarehouseLocation(), shelfLocation)) {
                return true;
            }
        }

        return false;
    }

    // returns a copy of the warehouse shelves
    public Map<Integer, Shelf> getShelvesData() {
        return new HashMap<>(shelves);
    }
}
