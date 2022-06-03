package src.ast.structures;

import src.ast.RunnableNode;
import src.ast.WarehouseRobotVisitor;
import src.ast.arugments.Name;

import java.util.List;

public class Every extends Structure {
    public Name varName;
    public Name orderName;
    public List<RunnableNode> runnableNodes;

    public Every(Name varName, Name orderName, List<RunnableNode> runnableNodes) {
        this.varName = varName;
        this.orderName = orderName;
        this.runnableNodes = runnableNodes;
        this.nodeTitle = "Every";
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
