package src.ast.conditionals;

import src.ast.WarehouseRobotVisitor;

public class If extends Conditional {
    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
