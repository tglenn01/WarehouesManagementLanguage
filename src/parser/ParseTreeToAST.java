package src.parser;

import src.ast.Node;
import src.ast.Program;

public class ParseTreeToAST extends WarehouseRobotParserBaseVisitor<Node> {

    @Override
    public Program visitProgram(WarehouseRobotParser.ProgramContext ctx) {
        return null;
    }
}
