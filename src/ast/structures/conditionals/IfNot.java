package src.ast.structures.conditionals;

import src.ast.RunnableNode;
import src.ast.WarehouseRobotVisitor;
import src.ast.expressions.Expression;

import java.util.List;

public class IfNot extends Conditional {
    public Expression expression;
    public List<RunnableNode> runnableNodes;

    public IfNot(Expression expression, List<RunnableNode> runnableNodes) {
        this.expression = expression;
        this.runnableNodes = runnableNodes;
        this.nodeTitle = "If Not";
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
