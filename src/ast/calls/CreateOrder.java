package src.ast.calls;

import src.ast.WarehouseRobotVisitor;
import src.ast.arugments.Product;
import src.model.Inventory;

import java.util.Map;

public class CreateOrder extends Create {
    public String orderName;
    public Inventory orderRequest;

    public CreateOrder(String orderName, Inventory orderRequest) {
        this.orderName = orderName;
        this.orderRequest = orderRequest;
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
