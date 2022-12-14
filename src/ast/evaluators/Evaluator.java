package src.ast.evaluators;

import exceptions.*;
import src.ast.Program;
import src.ast.RunnableNode;
import src.ast.WarehouseRobotVisitor;
import src.ast.arugments.Argument;
import src.ast.arugments.Name;
import src.ast.arugments.Num;
import src.ast.arugments.Product;
import src.ast.arugments.locations.FrontHouse;
import src.ast.arugments.locations.Shelf;
import src.ast.arugments.orders.CustomerOrder;
import src.ast.arugments.orders.FulfilledOrder;
import src.ast.arugments.orders.Order;
import src.ast.calls.CreateOrder;
import src.ast.calls.CreateProducts;
import src.ast.expressions.CheckOrderAvailability;
import src.ast.expressions.CheckProductAvailability;
import src.ast.expressions.Expression;
import src.ast.statements.*;
import src.ast.structures.Every;
import src.ast.structures.conditionals.If;
import src.ast.structures.conditionals.IfNot;
import src.model.Inventory;
import src.model.InventoryManager;
import src.model.Robot;
import src.model.Warehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Evaluator implements WarehouseRobotVisitor<StringBuilder, Integer> {
    private final Robot robot = InventoryManager.robot;
    private final Warehouse warehouse = InventoryManager.warehouse;
    private final Map<Name, Order> orderMap = new HashMap<>();
    private final Map<Name, Inventory> inventoryVarMap = new HashMap<>();
    private final Map<Name, Product> iterableMap = new HashMap<>();

    @Override
    public Integer visit(StringBuilder context, Program programNode) {
        for (RunnableNode statement : programNode.getRunnableNodes()) {
            statement.accept(context, this);
        }
        return null;
    }

    /**
     *  STRUCTURES
     */

    @Override
    public Integer visit(StringBuilder context, If ifNode) {
        Expression expression = ifNode.expression;
        List<RunnableNode> runnableNodes = ifNode.runnableNodes;

        context.append("Evaluating result of expression ")
                .append(expression.nodeTitle)
                .append(" to check if nodes ")
                .append(runnableNodes.stream().map(node -> node.nodeTitle).toList().toString())
                .append(" will run")
                .append(System.lineSeparator());

        if (expression.accept(context, this) == 1) {
            context.append(expression.nodeTitle).append(" returned true").append(System.lineSeparator());
            for (RunnableNode node : runnableNodes) {
                node.accept(context, this);
            }
        } else {
            context.append(expression.nodeTitle).append(" returned false").append(System.lineSeparator());
        }

        return null;
    }

    @Override
    public Integer visit(StringBuilder context, IfNot ifNotNode) {
        Expression expression = ifNotNode.expression;
        List<RunnableNode> runnableNodes = ifNotNode.runnableNodes;

        context.append("Evaluating result of expression ")
                .append(expression.nodeTitle)
                .append(" if not, nodes ")
                .append(runnableNodes.stream().map(node -> node.nodeTitle).toList().toString())
                .append(" will run")
                .append(System.lineSeparator());

        if (expression.accept(context, this) == 0) {
            for (RunnableNode node : runnableNodes) {
                node.accept(context, this);
            }
        }

        return null;
    }

    @Override
    public Integer visit(StringBuilder context, Every everyNode) {
        Order order = orderMap.get(everyNode.orderName);

        for (Map.Entry<Product, Num> entry : order.getOrderData().entrySet()) {
            context.append("Looping through product ").append(entry.getKey().getName().name).append(System.lineSeparator());

            iterableMap.put(everyNode.varName, entry.getKey());

            for (RunnableNode runnableNode : everyNode.runnableNodes) {
                runnableNode.accept(context, this);
            }

            iterableMap.remove(everyNode.varName);

            context.append("Finished looping product ").append(entry.getKey().getName().name).append(System.lineSeparator());
        }

        return null;
    }

    /**
     *  CALLS
     */

    @Override
    public Integer visit(StringBuilder context, CreateOrder createOrderNode) {
        Name orderName = createOrderNode.orderName;
        Inventory orderRequest = createOrderNode.orderRequest;
        CustomerOrder newOrder = new CustomerOrder(orderRequest);

        if (orderMap.containsKey(orderName)) {
            context.append("An order is already defined with the name ")
                    .append(orderName.name)
                    .append(" the old order will be overwritten which contained ")
                    .append(orderMap.get(orderName).getOrderData().keySet().stream().map(keyset -> keyset.getName().name).toList().toString())
                    .append(System.lineSeparator());
        }

        orderMap.put(orderName, newOrder);
        context.append("Created new order ")
                .append(orderName.name)
                .append(" with inventory ")
                .append(orderRequest.keySet().stream().map(keyset -> keyset.getName().name).toList().toString())
                .append(System.lineSeparator());

        robot.createNewInventory(orderName);

        return null;
    }

    @Override
    public Integer visit(StringBuilder context, CreateProducts createProductsNode) {
        Name productsName = createProductsNode.productsName;

        if (inventoryVarMap.containsKey(productsName)) {
            context.append("An inventory is already defined with the name ")
                    .append(productsName.name)
                    .append(" the old inventory will be overwritten which contained ")
                    .append(inventoryVarMap.get(productsName).keySet())
                    .append(System.lineSeparator());
        }

        inventoryVarMap.put(productsName, new Inventory());
        context.append("Created new product inventory ").append(productsName.name).append(System.lineSeparator());

        robot.createNewInventory(productsName);

        return null;
    }

    /**
     *  STATEMENTS
     */

    @Override
    public Integer visit(StringBuilder context, GoTo goToNode) {
        Argument argument = goToNode.argument;

        if (argument == FrontHouse.getInstance()) {
            context.append("Robot went to the front of house").append(System.lineSeparator());
            robot.goTo(FrontHouse.getInstance());
        } else if (argument.getClass() == Product.class) {
            Product product = (Product) argument;
            Integer shelfLocation = product.getProductShelfLocation();
            Shelf shelf = warehouse.getShelfAtLocation(shelfLocation);
            context.append("The robot went to shelf # ").append(shelfLocation).append(System.lineSeparator());
            robot.goTo(shelf);
        } else if (argument.getClass() == Name.class) {
            Name varName = (Name) goToNode.argument;
            Product product = iterableMap.get(varName);
            Integer shelfLocation = product.getProductShelfLocation();
            Shelf shelf = warehouse.getShelfAtLocation(shelfLocation);
            context.append("The robot went to shelf # ").append(shelfLocation).append(System.lineSeparator());
            robot.goTo(shelf);
        }

        return null;
    }

    @Override
    public Integer visit(StringBuilder context, PickUp pickUpNode) {
        Argument argument = pickUpNode.argument;


        try {
            if (argument.getClass() == Product.class) {
                robot.pickup(context, (Product) argument, pickUpNode.amount, pickUpNode.inventoryName);
            } else if (argument.getClass() == Name.class) {
                Product product = iterableMap.get(pickUpNode.argument);
                robot.pickup(context, product, pickUpNode.amount, pickUpNode.inventoryName);
            }
        } catch (ProductNotValidOnShelfException | InvalidLocationException | RobotDoesNotHaveInventoryException e) {
            context.append(e.getMessage());
        }

        return null;
    }

    @Override
    public Integer visit(StringBuilder context, DropOff dropOffNode) {
        Name inventoryName = dropOffNode.inventoryName;
        Argument argument = dropOffNode.argument;
        Product product;

        if (argument.getClass() == Product.class) {
            product = (Product) argument;
        } else if (dropOffNode.argument.getClass() == Name.class) {
            product = iterableMap.get(dropOffNode.argument);
            context.append("TODO implement loop support");
        } else {
            context.append("Drop off at invalid argument ").append(System.lineSeparator());
            return 0;
        }

        try {
            robot.dropOff(product, inventoryName);
            context.append("Robot dropped off ")
                    .append(product.getName())
                    .append(" at ")
                    .append(robot.getRobotLocation().getLocationName())
                    .append(System.lineSeparator());
        } catch (InvalidLocationException | ProductNotValidOnShelfException | RobotDoesNotHaveProductException | RobotDoesNotHaveInventoryException e) {
            context.append(e.getMessage());
        }

        return null;
    }

    @Override
    public Integer visit(StringBuilder context, Restock restockNode) {
        Product product = restockNode.product;
        Num amount = restockNode.amount;

        try {
            Shelf shelf = warehouse.getShelfAtLocation(product.getProductShelfLocation());

            shelf.restockProduct(product, amount);
            context.append("Robot restocked ")
                    .append(amount.number).append(" ")
                    .append(product.getName().name)
                    .append(" at location ")
                    .append(shelf.getLocationName())
                    .append(System.lineSeparator());
        } catch (ProductNotValidOnShelfException e) {
            context.append(e.getMessage());
        }

        return null;
    }

    @Override
    public Integer visit(StringBuilder context, Fulfill fulfillNode) {
        Name orderName = fulfillNode.customerOrderName;
        CustomerOrder customerOrder = (CustomerOrder) orderMap.get(orderName);

        try {
            context.append(robot.fulfill(customerOrder, fulfillNode.inventoryName));
        } catch (NotFrontOfHouseException | RobotDoesNotHaveInventoryException e) {
            context.append(e.getMessage());
        }

        return null;
    }

    @Override
    public Integer visit(StringBuilder context, Add addNode) {
        Num amount = addNode.amount;
        Product product = addNode.product;
        Name inventoryName = addNode.inventoryName;

        if (orderMap.containsKey(inventoryName)) {
            context.append("Adding ")
                    .append(amount.number)
                    .append(" ")
                    .append(product.getName().name)
                    .append(" to order ")
                    .append(inventoryName.name)
                    .append(System.lineSeparator());

            orderMap.get(inventoryName).add(product, amount);
        } else {
            context.append("Unable to find order with name ").append(inventoryName);
        }

        return null;
    }

    /**
     *  EXPRESSIONS:    return 1 == true / return 0 == false
     */

    @Override
    public Integer visit(StringBuilder context, CheckOrderAvailability checkOrderAvailabilityNode) {
        CustomerOrder customerOrder = (CustomerOrder) orderMap.get(checkOrderAvailabilityNode.orderName);

        for (Map.Entry<Product, Num> entry : customerOrder.getOrderData().entrySet()) {
            CheckProductAvailability checkProductAvailability = new CheckProductAvailability(entry.getKey(), entry.getValue());
            if (checkProductAvailability.accept(context, this) == 0) {
                return 0;
            }
        }

        return 1;
    }

    @Override
    public Integer visit(StringBuilder context, CheckProductAvailability checkProductAvailabilityNode) {
        Product product = checkProductAvailabilityNode.product;
        Num amount = checkProductAvailabilityNode.amount;

        try {

            if (warehouse.checkAvailability(product, amount)) {
                context.append(amount.number).append(" ").append(product.getName().name).append(" was available")
                        .append(System.lineSeparator());
                return 1;
            } else {
                context.append(amount.number).append(" ").append(product.getName().name).append(" was not available")
                        .append(System.lineSeparator());
                return 0;
            }

        } catch (ProductNotValidOnShelfException e) {
            context.append(e.getMessage());
        }

        return 0;
    }

    /**
     *  ARGUMENTS
     */

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
    public Integer visit(StringBuilder context, Num numberNode) {
        return numberNode.number;
    }

    @Override
    public Integer visit(StringBuilder context, Name nameNode) {
        return null;
    }
}
