package src.ast.statements;

import src.ast.WarehouseRobotVisitor;
import src.ast.arugments.Argument;
import src.ast.arugments.Name;
import src.ast.arugments.Num;

public class PickUp extends Statement {
    public Argument argument;
    public Num amount;
    public Name inventoryName;

    public PickUp(Argument argument, Num amount, Name inventoryName) {
        this.argument = argument;
        this.amount = amount;
        this.inventoryName = inventoryName;
        this.nodeTitle = "Pickup";
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
