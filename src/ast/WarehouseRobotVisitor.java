package src.ast;

import src.ast.arugments.Name;
import src.ast.arugments.Num;
import src.ast.arugments.Product;
import src.ast.arugments.locations.FrontHouse;
import src.ast.arugments.locations.Shelf;
import src.ast.calls.CreateOrder;
import src.ast.calls.CreateProducts;
import src.ast.expressions.CheckOrderAvailability;
import src.ast.structures.conditionals.If;
import src.ast.structures.conditionals.IfNot;
import src.ast.expressions.CheckProductAvailability;
import src.ast.arugments.orders.CustomerOrder;
import src.ast.arugments.orders.FulfilledOrder;
import src.ast.statements.*;
import src.ast.structures.Every;

public interface WarehouseRobotVisitor<C,T> {
    T visit(C context, Program programNode);

    // structures
    T visit(C context, If ifNode);
    T visit(C context, IfNot ifNotNode);
    T visit(C context, Every everyNode);

    // calls
    T visit(C context, CreateOrder createOrderNode);
    T visit(C context, CreateProducts createProductsNode);

    // statements
    T visit(C context, GoTo goToNode);
    T visit(C context, PickUp pickUpNode);
    T visit(C context, DropOff dropOffNode);
    T visit(C context, RestockOrder restockOrderNode);
    T visit(C context, Fulfill fulfillNode);
    T visit(C context, Add addNode);

    // expressions
    T visit(C context, CheckOrderAvailability checkOrderAvailabilityNode);
    T visit(C context, CheckProductAvailability checkProductAvailabilityNode);

    // arguments
    T visit(C context, Shelf shelfNode);
    T visit(C context, FrontHouse frontHouseNode);
    T visit(C context, CustomerOrder customerOrderNode);
    T visit(C context, FulfilledOrder fulfilledOrderNode);
    T visit(C context, Product productNode);
    T visit(C context, Num numberNode);
    T visit(C context, Name nameNode);
}
