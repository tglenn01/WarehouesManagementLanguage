package src.ast;

import exceptions.NoSuchProductException;
import src.ast.Node;
import src.ast.locations.Shelf;

import java.util.List;

// The warehouse where all products are stored in their respective shelves
public class Warehouse extends Node {
    private final static Integer NUMBER_OF_ROWS = 5;
    private final static Integer NUMBER_OF_COLUMNS = 5;

    private List<Shelf> shelves;


    public Warehouse() {
        // stub
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
}
