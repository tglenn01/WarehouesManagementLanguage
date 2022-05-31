package src.ast.statements;

import src.ast.WarehouseRobotVisitor;
import src.ast.arugments.locations.Location;

public class GoTo extends Statement {
    public Location location;

    public GoTo(Location location) {
        this.location = location;
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
