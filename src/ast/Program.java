package src.ast;

import java.util.List;

public class Program extends Node {
    private final List<RunnableNode> statements;

    public Program(List<RunnableNode> statements) {
        this.statements = statements;
        this.nodeTitle = "Program";
    }

    public List<RunnableNode> getRunnableNodes() {
        return statements;
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
