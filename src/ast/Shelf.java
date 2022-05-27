package src.ast;

import java.util.List;

public class Shelf extends Node {
    public final static int MAX_QUANTITY_OF_PRODUCT_PER_SHELF = 10;

    private List<Product> products;
    private int xLocation;
    private int yLocation;
}
