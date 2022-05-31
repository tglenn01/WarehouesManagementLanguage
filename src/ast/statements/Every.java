package src.ast.statements;

import src.ast.WarehouseRobotVisitor;

public class Every extends Statement {
    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
