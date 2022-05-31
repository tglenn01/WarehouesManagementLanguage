package src.ast.conditionals;

import src.ast.Call;
import src.ast.WarehouseRobotVisitor;

public class IfNot extends Call implements Conditional {
    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
