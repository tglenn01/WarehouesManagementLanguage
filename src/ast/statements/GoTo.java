package src.ast.statements;

import src.ast.WarehouseRobotVisitor;
import src.ast.arugments.Argument;

public class GoTo extends Statement {
    public Argument argument;

    public GoTo(Argument argument) {
        this.argument = argument;
        this.nodeTitle = "Go To";
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }

}
