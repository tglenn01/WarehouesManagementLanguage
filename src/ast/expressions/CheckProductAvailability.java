package src.ast.expressions;

import src.ast.WarehouseRobotVisitor;
import src.ast.arugments.Num;
import src.ast.arugments.Product;

public class CheckProductAvailability extends Expression {
    public Product product;
    public Num amount;

    public CheckProductAvailability(Product product, Num amount) {
        this.product = product;
        this.amount = amount;
        this.nodeTitle = "Check Product Availability";
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
