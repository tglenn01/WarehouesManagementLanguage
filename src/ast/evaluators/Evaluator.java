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
import src.ast.arugments.orders.Order;
import src.ast.calls.CreateOrder;
import src.ast.calls.CreateProducts;
import src.ast.structures.conditionals.If;
import src.ast.structures.conditionals.IfNot;
import src.ast.expressions.CheckAvailability;
import src.ast.arugments.orders.CustomerOrder;
import src.ast.arugments.orders.FulfilledOrder;
import src.ast.statements.*;
import src.ast.structures.Every;
import src.model.Inventory;
import src.model.InventoryManager;
import src.model.Robot;
import src.model.Warehouse;

import java.util.HashMap;
import java.util.Map;

public class Evaluator implements WarehouseRobotVisitor<StringBuilder, Integer> {
    private final Robot robot = InventoryManager.robot;
    private final Warehouse warehouse = InventoryManager.warehouse;
    private final Map<String, Order> orderMap = new HashMap<>();
    private final Map<String, Inventory> inventoryVarMap = new HashMap<>();

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
    public Integer visit(StringBuilder context, CreateOrder createOrderNode) {
        String orderName = createOrderNode.orderName;
        Inventory orderRequest = createOrderNode.orderRequest;
        CustomerOrder newOrder = new CustomerOrder(orderRequest);

        if (orderMap.containsKey(orderName)) {
            context.append("An order is already defined with the name ")
                    .append(orderName)
                    .append(" the old order will be overwritten which contained ")
                    .append(orderMap.get(orderName).getOrderData().keySet());
        }

        orderMap.put(orderName, newOrder);
        context.append("Created new order ")
                .append(orderName)
                .append(" with inventory ")
                .append(orderRequest.keySet());

        return null;
    }

    @Override
    public Integer visit(StringBuilder context, CreateProducts createProductsNode) {
        String productsName = createProductsNode.productsName;

        if (inventoryVarMap.containsKey(productsName)) {
            context.append("An inventory is already defined with the name ")
                    .append(productsName)
                    .append(" the old inventory will be overwritten which contained ")
                    .append(inventoryVarMap.get(productsName).keySet());
        }

        inventoryVarMap.put(productsName, new Inventory());
        context.append("Created new product inventory ").append(productsName);

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
