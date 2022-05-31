package src.ast;

import src.ast.statements.Statement;

import java.util.List;

public class Program extends Node {
    private final List<Statement> statements;

    public Program(List<Statement> statements) {
        this.statements = statements;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return null;
    }
}
