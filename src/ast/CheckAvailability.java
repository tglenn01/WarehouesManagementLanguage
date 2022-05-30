package src.ast;

public class CheckAvailability extends Node {
    public Product product;
    public Integer amount;

    public CheckAvailability(Product product, Integer amount) {
        this.product = product;
        this.amount = amount;
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
