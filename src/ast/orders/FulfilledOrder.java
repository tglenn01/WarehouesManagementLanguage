package src.ast.orders;

import src.ast.Product;
import src.ast.WarehouseRobotVisitor;

import java.util.Map;

// A fulfilled order that is deposited at the front of house
public class FulfilledOrder extends Order {

    public FulfilledOrder(Map<Product, Integer> fulfilledProducts) {
        order = fulfilledProducts;
    }


    /**
     *
     * Prints out what part of the order is fulfilled and which were out of stock
     *
     * @param customerOrder: the original customer order comparing with the fulfilled
     *
     * @return returns a string detailed if all items are fulfilled or some were skipped
     *
     */
    public String compareWithRequest(CustomerOrder customerOrder) {
        // stub
        return null;
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
