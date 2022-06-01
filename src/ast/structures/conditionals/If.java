package src.ast.structures.conditionals;

import src.ast.RunnableNode;
import src.ast.WarehouseRobotVisitor;
import src.ast.expressions.Expression;

import java.util.List;

public class If extends Conditional {
    public Expression expression;
    public List<RunnableNode> runnableNodes;

    public If(Expression expression, List<RunnableNode> runnableNodes) {
        this.expression = expression;
        this.runnableNodes = runnableNodes;
        this.nodeTitle = "If";
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
