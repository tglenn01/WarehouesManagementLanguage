package src.ast.calls;

import src.ast.WarehouseRobotVisitor;
import src.ast.arugments.Name;

public class CreateProducts extends Create {
    public Name productsName;


    public CreateProducts(Name productsName) {
        this.productsName = productsName;
        this.nodeTitle = "Create Products";
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
