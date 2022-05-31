package src.ast.calls;

import src.ast.WarehouseRobotVisitor;

public class CreateProducts extends Create {
    public String productsName;


    public CreateProducts(String productsName) {
        this.productsName = productsName;
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
