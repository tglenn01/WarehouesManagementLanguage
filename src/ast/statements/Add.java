package src.ast.statements;

import src.ast.WarehouseRobotVisitor;
import src.ast.arugments.Name;
import src.ast.arugments.Num;
import src.ast.arugments.Product;

public class Add extends Statement{
    public Num amount;
    public Product product;
    public Name inventoryName;

    public Add(Num amount, Product product, Name inventoryName) {
        this.amount = amount;
        this.product = product;
        this.inventoryName = inventoryName;
        this.nodeTitle = "Add";
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return null;
    }
}
