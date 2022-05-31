package src.ast.statements;

import src.ast.WarehouseRobotVisitor;
import src.ast.orders.CustomerOrder;

public class Fulfill extends Statement {
    public CustomerOrder customerOrder;

    public Fulfill(CustomerOrder customerOrder) {
        this.customerOrder  = customerOrder;
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
