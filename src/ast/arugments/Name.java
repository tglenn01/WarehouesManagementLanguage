package src.ast.arugments;

import src.ast.WarehouseRobotVisitor;

public class Name extends Argument {
    public String name;

    public Name(String name) {
        this.name = name;
        this.nodeTitle = "Name";
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return null;
    }
}
