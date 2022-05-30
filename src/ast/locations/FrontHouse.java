package src.ast.locations;

import src.ast.Node;
import src.ast.WarehouseRobotVisitor;
import src.ast.orders.CustomerOrder;
import src.ast.orders.FulfilledOrder;

// the front of house where all orders are fulfilled
public class FrontHouse extends Node implements Location {
    private static FrontHouse frontHouse;

    private FrontHouse() {}

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
     */
    public void fulfill(CustomerOrder customerOrder, FulfilledOrder fulfilledOrder) {
        // stub
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
