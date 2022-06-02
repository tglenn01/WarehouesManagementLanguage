package src.ast.arugments.orders;

import src.ast.WarehouseRobotVisitor;
import src.ast.arugments.Num;
import src.ast.arugments.Product;
import src.model.Inventory;

import java.util.Map;

// A fulfilled order that is deposited at the front of house
public class FulfilledOrder extends Order {

    public FulfilledOrder(Inventory fulfilledProducts) {
        order = fulfilledProducts;
        this.nodeTitle = "Fulfilled Order";
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

        for (Map.Entry<Product, Num> entry : customerOrder.getOrderData().entrySet()) {
            Product product = entry.getKey();
            Num amountNeeded = entry.getValue();

            Num amountOfProductsFulfilled = this.order.get(product);

            if (amountOfProductsFulfilled.number == 0) {
                compareResults.append("We were out of stock of ").append(product.getName().name).append(System.lineSeparator());
            } else if (amountOfProductsFulfilled.number < amountNeeded.number) {
                compareResults.append("We only had ").append(amountOfProductsFulfilled.number).append(" Of ").append(product.getName().name).append(System.lineSeparator());
            } else {
                compareResults.append("We had all of the ").append(product.getName().name).append(" you requested!").append(System.lineSeparator());
            }

        }

        return compareResults;
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
