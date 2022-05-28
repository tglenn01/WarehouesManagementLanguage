package src.ast.orders;

import src.ast.Node;
import src.ast.Product;

import java.util.Map;

public abstract class Order extends Node {
    protected Map<Product, Integer> order;

    // Adds product to order with given amount
    public void add(Product product, Integer amount) {
        // stub
    }
}
