package src.ast;

import src.ast.locations.Location;

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
