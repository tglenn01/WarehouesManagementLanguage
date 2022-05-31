package src.ast.expressions;

import src.ast.WarehouseRobotVisitor;
import src.ast.arugments.Product;

public class CheckAvailability extends Expression {
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
