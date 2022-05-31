package src.ast;

import src.ast.arugments.Num;
import src.ast.arugments.Product;
import src.ast.arugments.locations.FrontHouse;
import src.ast.arugments.locations.Shelf;
import src.ast.calls.Create;
import src.ast.conditionals.If;
import src.ast.conditionals.IfNot;
import src.ast.expressions.CheckAvailability;
import src.ast.orders.CustomerOrder;
import src.ast.orders.FulfilledOrder;
import src.ast.statements.*;

public interface WarehouseRobotVisitor<C,T> {
    T visit(C context, If ifNode);
    T visit(C context, IfNot ifNotNode);
    T visit(C context, GoTo goToNode);
    T visit(C context, PickUp pickUpNode);
    T visit(C context, DropOff dropOffNode);
    T visit(C context, RestockOrder restockOrderNode);
    T visit(C context, Create createNode);
    T visit(C context, Fulfill fulfillNode);
    T visit(C context, Every everyNode);
    T visit(C context, CheckAvailability checkAvailabilityNode);
    T visit(C context, Product productNode);
    T visit(C context, Shelf shelfNode);
    T visit(C context, FrontHouse frontHouseNode);
    T visit(C context, CustomerOrder customerOrderNode);
    T visit(C context, FulfilledOrder fulfilledOrderNode);
    T visit(C context, Program programNode);
    T visit(C context, Num numberNode);
}
