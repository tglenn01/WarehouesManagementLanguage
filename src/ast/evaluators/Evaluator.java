package src.ast.evaluators;

import exceptions.InvalidLocationException;
import exceptions.NotFrontOfHouseException;
import exceptions.ProductNotValidOnShelfException;
import exceptions.RobotDoesNotHaveException;
import src.ast.Program;
import src.ast.WarehouseRobotVisitor;
import src.ast.arugments.Num;
import src.ast.arugments.Product;
import src.ast.arugments.locations.FrontHouse;
import src.ast.arugments.locations.Location;
import src.ast.arugments.locations.Shelf;
import src.ast.calls.Create;
import src.ast.conditionals.If;
import src.ast.conditionals.IfNot;
import src.ast.expressions.CheckAvailability;
import src.ast.arugments.orders.CustomerOrder;
import src.ast.arugments.orders.FulfilledOrder;
import src.ast.statements.*;
import src.model.InventoryManager;
import src.model.Robot;
import src.model.Warehouse;

public class Evaluator implements WarehouseRobotVisitor<StringBuilder, Integer> {
    private final Robot robot = InventoryManager.robot;
    private final Warehouse warehouse = InventoryManager.warehouse;

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
        Location location = goToNode.location;

        robot.goTo(location);
        context.append("Robot went to new location ").append(location.getLocationName());

        return null;
    }

    @Override
    public Integer visit(StringBuilder context, PickUp pickUpNode) {

        try {
            robot.pickup(context, pickUpNode.product, pickUpNode.amount);
        } catch (ProductNotValidOnShelfException | InvalidLocationException e) {
            context.append(e.getMessage());
        }

        return null;
    }

    @Override
    public Integer visit(StringBuilder context, DropOff dropOffNode) {
        Product product = dropOffNode.product;
        try {
            robot.dropOff(product);
            context.append("Robot dropped off ")
                    .append(product.getName())
                    .append(" at ")
                    .append(robot.getRobotLocation().getLocationName());
        } catch (InvalidLocationException | ProductNotValidOnShelfException | RobotDoesNotHaveException e) {
            context.append(e.getMessage());
        }

        return null;
    }

    @Override
    public Integer visit(StringBuilder context, RestockOrder restockOrderNode) {
        Shelf shelf = restockOrderNode.shelf;
        Product product = restockOrderNode.product;
        Integer amount = restockOrderNode.amount;

        try {
            shelf.restockProduct(product, amount);
            context.append("Robot restocked ")
                    .append(amount).append(" ")
                    .append(product.getName())
                    .append(" at location ")
                    .append(shelf.getLocationName());
        } catch (ProductNotValidOnShelfException e) {
            context.append(e.getMessage());
        }

        return null;
    }

    @Override
    public Integer visit(StringBuilder context, Create createNode) {
        return null;
    }

    @Override
    public Integer visit(StringBuilder context, Fulfill fulfillNode) {
        CustomerOrder customerOrder = fulfillNode.customerOrder;

        try {
            // TODO Come back and make sure this works
            context.append(robot.fulfill(customerOrder));
        } catch (NotFrontOfHouseException e) {
            context.append(e.getMessage());
        }

        return null;
    }

    @Override
    public Integer visit(StringBuilder context, Every everyNode) {
        return null;
    }


    @Override
    // 1 = true; 0 = false;
    public Integer visit(StringBuilder context, CheckAvailability checkAvailabilityNode) {
        Product product = checkAvailabilityNode.product;
        Integer amount = checkAvailabilityNode.amount;

        try {

            if (warehouse.checkAvailability(product, amount)) {
                context.append(product.getName()).append(" was available");
                return 1;
            } else {
                context.append(product.getName()).append(" was not available");
                return 0;
            }

        } catch (ProductNotValidOnShelfException e) {
            context.append(e.getMessage());
        }

        return 0;
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

    @Override
    public Integer visit(StringBuilder context, Program programNode) {
        for (Statement statement : programNode.getStatements()) {
            statement.accept(context, this);
        }
        return null;
    }

    @Override
    public Integer visit(StringBuilder context, Num numberNode) {
        return numberNode.number;
    }
}
