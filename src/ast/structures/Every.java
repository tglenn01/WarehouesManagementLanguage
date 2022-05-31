package src.ast.structures;

import src.ast.WarehouseRobotVisitor;

public class Every extends Structure {
    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
