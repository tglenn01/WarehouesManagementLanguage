package src.ast.calls;

// input option


import src.ast.WarehouseRobotVisitor;

public class Create extends Call {



    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
