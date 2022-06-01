package src.ast.statements;

import src.ast.WarehouseRobotVisitor;
import src.ast.arugments.Name;
import src.ast.arugments.orders.CustomerOrder;

public class Fulfill extends Statement {
    public CustomerOrder customerOrder;
    public Name inventoryName;

    public Fulfill(CustomerOrder customerOrder, Name inventoryName) {
        this.customerOrder  = customerOrder;
        this.inventoryName = inventoryName;
        this.nodeTitle = "Fulfill";
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
