package src.ast.calls;

import src.ast.WarehouseRobotVisitor;
import src.ast.arugments.Name;
import src.model.Inventory;

public class CreateOrder extends Create {
    public Name orderName;
    public Inventory orderRequest;

    public CreateOrder(Name orderName, Inventory orderRequest) {
        this.orderName = orderName;
        this.orderRequest = orderRequest;
        this.nodeTitle = "Create Order";
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
