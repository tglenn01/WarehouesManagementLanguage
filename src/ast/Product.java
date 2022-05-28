package src.ast;

import src.ast.locations.Shelf;

// An item that has a name and assigned shelf
public class Product extends Node {
    private String name;
    private Shelf shelf;

    // must have a shelf when initialized
    public Product(Shelf shelf) {
        this.shelf = shelf;
    }

    /**
     *
     * Updates the shelf at which the product is stored, removes this product from the old shelf so it cannot be in
     * two shelves at once
     *
     * @param shelf: The new shelf location
     *
     */
    public void updateShelf(Shelf shelf) {
        // stub
    }

    // return the shelf that the products is stored at
    public Shelf getProductShelf() {
        return shelf;
    }
}
