package src.ast;

import src.ast.conditionals.If;
import src.ast.conditionals.IfNot;
import src.ast.locations.FrontHouse;
import src.ast.locations.Shelf;
import src.ast.orders.CustomerOrder;
import src.ast.orders.FulfilledOrder;

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
}
