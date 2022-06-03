package src.ast.arugments.orders;

import src.ast.arugments.Argument;
import src.ast.arugments.Num;
import src.ast.arugments.Product;
import src.model.Inventory;

public abstract class Order extends Argument {
    protected Inventory order;

    // Adds product to order with given amount
    public void add(Product product, Num amount) {
        Num amountToAdd;

        if (order.containsKey(product)) {
            amountToAdd = Num.add(order.get(product), amount);
        } else {
            amountToAdd = amount;
        }

        order.put(product, amountToAdd);
    }

    public Inventory getOrderData() {
        return new Inventory(order);
    }
}
