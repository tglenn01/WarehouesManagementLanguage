package src.ast.statements;

import src.ast.WarehouseRobotVisitor;
import src.ast.arugments.Product;

public class DropOff extends Statement {
    public Product product;

    public DropOff(Product product) {
        this.product = product;
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
