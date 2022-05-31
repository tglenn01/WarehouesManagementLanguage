package src.ast.arugments.orders;

import src.ast.WarehouseRobotVisitor;
import src.ast.arugments.Product;
import src.model.Inventory;

import java.util.Map;

// A fulfilled order that is deposited at the front of house
public class FulfilledOrder extends Order {

    public FulfilledOrder(Inventory fulfilledProducts) {
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
    public StringBuilder compareWithRequest(CustomerOrder customerOrder) {
        StringBuilder compareResults = new StringBuilder();

        for (Map.Entry<Product, Integer> entry : customerOrder.getOrderData().entrySet()) {
            Product product = entry.getKey();
            Integer amountNeeded = entry.getValue();

            Integer amountOfProductsFulfilled = this.order.get(product);

            if (amountOfProductsFulfilled == 0) {
                compareResults.append("We were out of stock of ").append(product.getName());
            } else if (amountOfProductsFulfilled < amountNeeded) {
                compareResults.append("We only had ").append(amountOfProductsFulfilled).append(" Of ").append(product.getName());
            } else {
                compareResults.append("We had all of the ").append(product.getName()).append(" you requested!");
            }

        }

        return compareResults;
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
