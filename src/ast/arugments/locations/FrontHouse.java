package src.ast.arugments.locations;

import src.ast.WarehouseRobotVisitor;
import src.ast.arugments.Argument;
import src.ast.arugments.orders.CustomerOrder;
import src.ast.arugments.orders.FulfilledOrder;

// the front of house where all orders are fulfilled
public class FrontHouse extends Argument implements Location {
    private static FrontHouse frontHouse;

    private FrontHouse() {
        this.nodeTitle = "Front House";
    }

    public static FrontHouse getInstance() {
        if (frontHouse == null) {
            frontHouse = new FrontHouse();
        }

        return frontHouse;
    }

    /**
     * Gives the customer the product and tells them details on if the whole product was fulfilled
     *
     * @param customerOrder: The customer order
     * @param fulfilledOrder: The fulfilled order given to the customer
     *
     * @return returns a string expressing how the order went
     *
     */
    public StringBuilder fulfill(CustomerOrder customerOrder, FulfilledOrder fulfilledOrder) {
        StringBuilder fulfilledRequest = fulfilledOrder.compareWithRequest(customerOrder);
        return fulfilledRequest;
    }

    public String getLocationName() {
        return "Front of House";
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
