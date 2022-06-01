package src.ast.expressions;

import src.ast.WarehouseRobotVisitor;
import src.ast.arugments.Num;
import src.ast.arugments.Product;

public class CheckAvailability extends Expression {
    public Product product;
    public Num amount;

    public CheckAvailability(Product product, Num amount) {
        this.product = product;
        this.amount = amount;
        this.nodeTitle = "Check Availability";
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
