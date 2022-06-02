package src.ast.expressions;

import src.ast.WarehouseRobotVisitor;
import src.ast.arugments.Name;
import src.ast.arugments.Num;
import src.ast.arugments.Product;

public class CheckOrderAvailability extends Expression {
    public Name orderName;

    public CheckOrderAvailability(Name orderName) {
        this.orderName = orderName;
        this.nodeTitle = "Check Order Availability";
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return null;
    }
}
