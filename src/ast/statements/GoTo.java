package src.ast.statements;

import src.ast.Node;
import src.ast.WarehouseRobotVisitor;
import src.ast.arugments.Argument;
import src.ast.arugments.Name;
import src.ast.arugments.Product;
import src.ast.arugments.locations.FrontHouse;
import src.ast.arugments.locations.Location;
import src.model.InventoryManager;
import src.model.ProductMasterList;
import src.model.Warehouse;

public class GoTo extends Statement {
    public Argument argument;

    public GoTo(Argument argument) {
        this.argument = argument;
        this.nodeTitle = "Go To";
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }

}
