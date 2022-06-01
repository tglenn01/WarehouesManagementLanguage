package src.ast.arugments.orders;

import src.ast.WarehouseRobotVisitor;
import src.model.Inventory;

// A Customer Order with a number of products and how much they need of each
public class CustomerOrder extends Order {

    public CustomerOrder(Inventory requestedProducts) {
        order = requestedProducts;
        this.nodeTitle = "Custom Order";
    }

    public CustomerOrder() {
        order = new Inventory();
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
