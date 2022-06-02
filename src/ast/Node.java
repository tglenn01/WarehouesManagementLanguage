package src.ast;

// ast Node
public abstract class Node {
    public String nodeTitle;

    abstract public <C,T> T accept(C context, WarehouseRobotVisitor<C,T> v);
}
