package src.ast.structures.conditionals;

import src.ast.WarehouseRobotVisitor;

public class IfNot extends Conditional {
    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
