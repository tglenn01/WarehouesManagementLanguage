package src.ast.statements;

import src.ast.WarehouseRobotVisitor;
import src.ast.arugments.Product;
import src.ast.arugments.locations.Shelf;

public class RestockOrder extends Statement {
    public Shelf shelf;
    public Product product;
    public Integer amount;

    public RestockOrder(Shelf shelf, Product product, Integer amount) {
        this.shelf = shelf;
        this.product = product;
        this.amount = amount;
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
