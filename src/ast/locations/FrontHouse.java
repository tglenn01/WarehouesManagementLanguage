package src.ast.locations;

import src.ast.orders.CustomerOrder;
import src.ast.orders.FulfilledOrder;

// the front of house where all orders are fulfilled
public class FrontHouse implements Location {
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

    }
}
