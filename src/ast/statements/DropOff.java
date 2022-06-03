package src.ast.statements;

import src.ast.WarehouseRobotVisitor;
import src.ast.arugments.Argument;
import src.ast.arugments.Name;

public class DropOff extends Statement {
    public Argument argument;
    public Name inventoryName;

    public DropOff(Argument argument, Name inventoryName) {
        this.argument = argument;
        this.inventoryName = inventoryName;
        this.nodeTitle = "Drop Off";
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
