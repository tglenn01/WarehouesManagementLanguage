package src.ast.evaluators;

import src.ast.*;
import src.ast.conditionals.If;
import src.ast.conditionals.IfNot;
import src.ast.locations.FrontHouse;
import src.ast.locations.Shelf;
import src.ast.orders.CustomerOrder;
import src.ast.orders.FulfilledOrder;

public class Evaluator implements WarehouseRobotVisitor<StringBuilder, Integer> {

    @Override
    public Integer visit(StringBuilder context, If ifNode) {
        return null;
    }

    @Override
    public Integer visit(StringBuilder context, IfNot ifNotNode) {
        return null;
    }

    @Override
    public Integer visit(StringBuilder context, GoTo goToNode) {
        return null;
    }

    @Override
    public Integer visit(StringBuilder context, PickUp pickUpNode) {
        return null;
    }

    @Override
    public Integer visit(StringBuilder context, DropOff dropOffNode) {
        return null;
    }

    @Override
    public Integer visit(StringBuilder context, RestockOrder restockOrderNode) {
        return null;
    }

    @Override
    public Integer visit(StringBuilder context, Create createNode) {
        return null;
    }

    @Override
    public Integer visit(StringBuilder context, Fulfill fulfillNode) {
        return null;
    }

    @Override
    public Integer visit(StringBuilder context, Every everyNode) {
        return null;
    }

    @Override
    public Integer visit(StringBuilder context, CheckAvailability checkAvailabilityNode) {
        return null;
    }

    @Override
    public Integer visit(StringBuilder context, Product productNode) {
        return null;
    }

    @Override
    public Integer visit(StringBuilder context, Shelf shelfNode) {
        return null;
    }

    @Override
    public Integer visit(StringBuilder context, FrontHouse frontHouseNode) {
        return null;
    }

    @Override
    public Integer visit(StringBuilder context, CustomerOrder customerOrderNode) {
        return null;
    }

    @Override
    public Integer visit(StringBuilder context, FulfilledOrder fulfilledOrderNode) {
        return null;
    }
}
