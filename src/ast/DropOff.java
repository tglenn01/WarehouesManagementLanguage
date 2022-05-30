package src.ast;

public class DropOff extends Node {
    public Product product;

    public DropOff(Product product) {
        this.product = product;
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
