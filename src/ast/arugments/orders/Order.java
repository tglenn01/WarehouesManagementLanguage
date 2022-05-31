package src.ast.arugments.orders;

import src.ast.arugments.Argument;
import src.ast.arugments.Product;

import java.util.HashMap;
import java.util.Map;

public abstract class Order extends Argument {
    protected Map<Product, Integer> order;

    // Adds product to order with given amount
    public void add(Product product, Integer amount) {
        int amountToAdd;

        if (order.containsKey(product)) {
            amountToAdd = order.get(product) + amount;
        } else {
            amountToAdd = amount;
        }

        order.put(product, amountToAdd);
    }

    public Map<Product, Integer> getOrderData() {
        return new HashMap<>(order);
    }
}
