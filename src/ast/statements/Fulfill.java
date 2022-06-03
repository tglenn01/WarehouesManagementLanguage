package src.ast.statements;

import src.ast.WarehouseRobotVisitor;
import src.ast.arugments.Name;

public class Fulfill extends Statement {
    public Name customerOrderName;
    public Name inventoryName;

    public Fulfill(Name customerOrderName, Name inventoryName) {
        this.customerOrderName  = customerOrderName;
        this.inventoryName = inventoryName;
        this.nodeTitle = "Fulfill";
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
