package src.ast;

import src.ast.orders.CustomerOrder;

public class Fulfill extends Node {
    public CustomerOrder customerOrder;

    public Fulfill(CustomerOrder customerOrder) {
        this.customerOrder  = customerOrder;
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
