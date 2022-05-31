package src.ast;

public class PickUp extends Statement {
    public Product product;
    public Integer amount;

    public PickUp(Product product, Integer amount) {
        this.product = product;
        this.amount = amount;
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
