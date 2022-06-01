package src.ast.statements;

import src.ast.WarehouseRobotVisitor;
import src.ast.arugments.Name;
import src.ast.arugments.Num;
import src.ast.arugments.Product;
import src.ast.arugments.locations.Shelf;

public class RestockOrder extends Statement {
    public static final int NUM_RESTOCK = 10;

    public Shelf shelf;
    public Product product;
    public Num amount;

    public RestockOrder(Shelf shelf, Product product, Num amount) {
        this.shelf = shelf;
        this.product = product;
        this.amount = amount;
        this.nodeTitle = "Restock";
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
