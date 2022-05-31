package src.ast;

import src.ast.WarehouseRobotVisitor;

// ast Node
public abstract class Node {
    abstract public <C,T> T accept(C context, WarehouseRobotVisitor<C,T> v);
}
