package src.ast;

import src.ast.locations.Shelf;

public class RestockOrder extends Node {
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
