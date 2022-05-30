package src.model;

import exceptions.InvalidLocationException;
import exceptions.NoSuchProductException;
import src.ast.Node;
import src.ast.Product;
import src.ast.locations.Shelf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     *
     * Check if there is enough amount of the given product
     *
     * @param product: Product to check
     * @param amount: Amount needed
     *
     * @throws NoSuchProductException: if the product we are asking for could not be found
     *
     * @return true if available else false
     *
     */
    public boolean checkAvailability(Product product, Integer amount) throws NoSuchProductException {
        // stub
        return false;
    }


    /**
     * Updates shelf at given location
     *
     * @param shelf: shelf to be replaced
     * @param location: shelf location
     *
     */
    public void updateShelf(Shelf shelf, Integer location) throws InvalidLocationException {

    }


    public Shelf getShelfAtLocation(Integer location) {
        return shelves.get(location);
    }

    // returns a copy of the warehouse shelves
    public Map<Integer, Shelf> getShelvesData() {
        return new HashMap<>(shelves);
    }
}
