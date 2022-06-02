package src.ast.structures;

import src.ast.RunnableNode;
import src.ast.WarehouseRobotVisitor;
import src.ast.arugments.Name;

import java.util.List;

public class Every extends Structure {
    public Name varName;
    public Name iterableName;
    public List<RunnableNode> runnableNodes;

    public Every(Name varName, Name iterableName, List<RunnableNode> runnableNodes) {
        this.varName = varName;
        this.iterableName = iterableName;
        this.runnableNodes = runnableNodes;
        this.nodeTitle = "Every";
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
