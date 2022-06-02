package src.ast.statements;

import src.ast.WarehouseRobotVisitor;
import src.ast.arugments.Num;
import src.ast.arugments.Product;

public class Restock extends Statement {
    public static final int NUM_RESTOCK = 10;

    public Product product;
    public Num amount;

    public Restock(Product product, Num amount) {
        this.product = product;
        this.amount = amount;
        this.nodeTitle = "Restock";
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
