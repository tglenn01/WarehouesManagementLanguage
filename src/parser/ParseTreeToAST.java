package src.parser;

import exceptions.NoSuchProductException;
import src.ast.Node;
import src.ast.Program;
import src.ast.RunnableNode;
import src.ast.arugments.Name;
import src.ast.arugments.Num;
import src.ast.arugments.Product;
import src.ast.arugments.locations.FrontHouse;
import src.ast.calls.Call;
import src.ast.calls.CreateOrder;
import src.ast.calls.CreateProducts;
import src.ast.expressions.CheckOrderAvailability;
import src.ast.expressions.CheckProductAvailability;
import src.ast.expressions.Expression;
import src.ast.statements.*;
import src.ast.structures.Every;
import src.ast.structures.Structure;
import src.ast.structures.conditionals.If;
import src.ast.structures.conditionals.IfNot;
import src.model.Inventory;
import src.model.ProductMasterList;

import java.util.ArrayList;
import java.util.List;

public class ParseTreeToAST extends WarehouseRobotParserBaseVisitor<Node> {

    @Override
    public Program visitProgram(WarehouseRobotParser.ProgramContext ctx) {
        List<RunnableNode> runnableNodes = new ArrayList<>();

        for (WarehouseRobotParser.Runnable_nodesContext n : ctx.runnable_nodes()) {
            runnableNodes.add(this.visitRunnable_nodes(n));
        }

        return new Program(runnableNodes);
    }

    @Override
    public RunnableNode visitRunnable_nodes(WarehouseRobotParser.Runnable_nodesContext ctx) {

        if (ctx.statement() != null) {
            return visitStatement(ctx.statement());
        } else if (ctx.call() != null) {
            return visitCall(ctx.call());
        } else if (ctx.structure() != null) {
            return visitStructure(ctx.structure());
        } else {
            throw new RuntimeException("Statement parse tree with invalid context information");
        }
    }

    @Override
    public Node visitArgument(WarehouseRobotParser.ArgumentContext ctx) {
        return super.visitArgument(ctx);
    }

    @Override
    public Call visitCall(WarehouseRobotParser.CallContext ctx) {

        if (ctx.create_order() != null) {
            return visitCreate_order(ctx.create_order());
        } else if (ctx.create_products() != null) {
            return visitCreate_products(ctx.create_products());
        } else {
            throw new RuntimeException("Statement parse tree with invalid context information");
        }

    }

    @Override
    public Structure visitStructure(WarehouseRobotParser.StructureContext ctx) {

        if (ctx.if_() != null) {
            return visitIf(ctx.if_());
        } else if (ctx.if_not() != null) {
            return visitIf_not(ctx.if_not());
        } else if (ctx.loop() != null) {
            return visitLoop(ctx.loop());
        } else {
            throw new RuntimeException("Statement parse tree with invalid context information");
        }

    }

    @Override
    public Statement visitStatement(WarehouseRobotParser.StatementContext ctx) {

        if (ctx.goto_() != null) {
            return visitGoto(ctx.goto_());
        } else if (ctx.pickup() != null) {
            return visitPickup(ctx.pickup());
        } else if (ctx.dropoff() != null) {
            return visitDropoff(ctx.dropoff());
        } else if (ctx.restock() != null) {
            return visitRestock(ctx.restock());
        } else if (ctx.fulfill() != null) {
            return visitFulfill(ctx.fulfill());
        } else if (ctx.add() != null) {
            return visitAdd(ctx.add());
        } else {
            throw new RuntimeException("Statement parse tree with invalid context information");
        }

    }

    @Override
    public Expression visitExpression(WarehouseRobotParser.ExpressionContext ctx) {

        if (ctx.check_order_availability() != null) {
            return visitCheck_order_availability(ctx.check_order_availability());
        } else if(ctx.check_product_availability() != null) {
            return visitCheck_product_availability(ctx.check_product_availability());
        } else {
            throw new RuntimeException("Statement parse tree with invalid context information");
        }

    }

    @Override
    public Name visitOrder_varname(WarehouseRobotParser.Order_varnameContext ctx) {

        if (ctx.CUSTOMER_ORDER() != null) {
            return new Name(ctx.CUSTOMER_ORDER().getText());
        } else {
            throw new RuntimeException("Statement parse tree with invalid context information");
        }
    }

    @Override
    public Name visitProducts_varname(WarehouseRobotParser.Products_varnameContext ctx) {

        if (ctx.RETURN_PRODUCTS() != null) {
            return new Name(ctx.RETURN_PRODUCTS().getText());
        } else {
            throw new RuntimeException("Statement parse tree with invalid context information");
        }
    }

    @Override
    public Name visitVariable_varname(WarehouseRobotParser.Variable_varnameContext ctx) {

        if (ctx.VARIABLE_NAME() != null) {
            return new Name(ctx.VARIABLE_NAME().getText());
        } else {
            throw new RuntimeException("Statement parse tree with invalid context information");
        }

    }

    @Override
    public CreateOrder visitCreate_order(WarehouseRobotParser.Create_orderContext ctx) {

        Name orderName = new Name(ctx.order_varname().CUSTOMER_ORDER().getText());
        Inventory inventory = new Inventory();

        int numProductsAdded = ctx.NUM().size();

        for (int i = 0; i < numProductsAdded; i++) {
            Name productName = new Name(ctx.PRODUCT(i).getText());
            Num numProduct = new Num(Integer.parseInt(ctx.NUM(i).getText()));

            try {
                Product product = ProductMasterList.getProductGivenName(productName);
                inventory.put(product, numProduct);
            } catch (NoSuchProductException e) {
                throw new RuntimeException("Failed to find product " + productName.name);
            }
        }

        return new CreateOrder(orderName, inventory);

    }

    @Override
    public CreateProducts visitCreate_products(WarehouseRobotParser.Create_productsContext ctx) {
        return new CreateProducts(new Name(ctx.products_varname().RETURN_PRODUCTS().getText()));
    }

    @Override
    public Every visitLoop(WarehouseRobotParser.LoopContext ctx) {
        Name variableName = new Name(ctx.variable_varname().VARIABLE_NAME().getText());
        Name orderName = new Name(ctx.order_varname().CUSTOMER_ORDER().getText());
        List<RunnableNode> runnableNodes = new ArrayList<>();

        for (WarehouseRobotParser.Runnable_nodesContext runnableNodesContext : ctx.runnable_nodes()) {
            runnableNodes.add(visitRunnable_nodes(runnableNodesContext));
        }

        return new Every(variableName, orderName, runnableNodes);
    }

    @Override
    public If visitIf(WarehouseRobotParser.IfContext ctx) {
        if (ctx.expression() == null) {
            throw new RuntimeException("Statement parse tree with invalid context information");
        }

        Expression expression = visitExpression(ctx.expression());
        List<RunnableNode> runnableNodes = new ArrayList<>();

        for (WarehouseRobotParser.Runnable_nodesContext runnableNodesContext : ctx.runnable_nodes()) {
            runnableNodes.add(visitRunnable_nodes(runnableNodesContext));
        }

        return new If(expression, runnableNodes);
    }

    @Override
    public IfNot visitIf_not(WarehouseRobotParser.If_notContext ctx) {
        if (ctx.expression() == null) {
            throw new RuntimeException("Statement parse tree with invalid context information");
        }

        Expression expression = visitExpression(ctx.expression());
        List<RunnableNode> runnableNodes = new ArrayList<>();

        for (WarehouseRobotParser.Runnable_nodesContext runnableNodesContext : ctx.runnable_nodes()) {
            runnableNodes.add(visitRunnable_nodes(runnableNodesContext));
        }

        return new IfNot(expression, runnableNodes);
    }

    @Override
    public GoTo visitGoto(WarehouseRobotParser.GotoContext ctx) {
        if (ctx.VARIABLE_PRODUCT() != null) {

            try {
                Product product = ProductMasterList.getProductGivenName( new Name(ctx.VARIABLE_PRODUCT().getText()));

                if (product.getProductShelfLocation() == 0) {
                    throw new RuntimeException("Attempting to reach a product that" +
                            " is not currently assigned to a shelf");
                }

                return new GoTo(product);
            } catch (NoSuchProductException e) {
                e.printStackTrace();
                throw new RuntimeException("Attempting to reach a product that does not exist");
            }
        } else if (ctx.VARIABLE_FRONTHOUSE() != null) {

            return new GoTo(FrontHouse.getInstance());

        } else if (ctx.variable_varname() != null) {
            return new GoTo(new Name(ctx.variable_varname().VARIABLE_NAME().getText()));
        } else {
            throw new RuntimeException("Statement parse tree with invalid context information");
        }
    }

    @Override
    public PickUp visitPickup(WarehouseRobotParser.PickupContext ctx) {
        Num amount = new Num(Integer.parseInt(ctx.NUM_VARIABLE().getText()));
        Name inventoryName = new Name(ctx.products_varname().getText());


        if (ctx.VARIABLE_PRODUCT() != null) {
            try {
                // get the product
                Product product = ProductMasterList.getProductGivenName(new Name(ctx.VARIABLE_PRODUCT().getText()));

                // check that the product is currently assigned to a shelf
                if (product.getProductShelfLocation() == 0) {
                    throw new RuntimeException("Attempting to reach a product that" +
                            " is not currently assigned to a shelf");
                }

                // return the new PickUp
                return new PickUp(product, amount, inventoryName);

            } catch (NoSuchProductException e) {

                throw new RuntimeException(e.getMessage());

            }
        } else if (ctx.variable_varname() != null) {
            return new PickUp(new Name (ctx.variable_varname().VARIABLE_NAME().getText()), amount, inventoryName);
        } else {
            throw new RuntimeException("Statement parse tree with invalid context information");
        }
    }

    @Override
    public DropOff visitDropoff(WarehouseRobotParser.DropoffContext ctx) {
        Name inventoryName = new Name(ctx.products_varname().getText());

        if (ctx.VARIABLE_PRODUCT() != null) {
            try {
                Product product = ProductMasterList.getProductGivenName(new Name(ctx.VARIABLE_PRODUCT().getText()));
                return new DropOff(product, inventoryName);
            } catch (NoSuchProductException e) {
                throw new RuntimeException(e.getMessage());
            }
        } else if (ctx.variable_varname() != null) {
            Name variableName = new Name(ctx.variable_varname().VARIABLE_NAME().getText());

            return new DropOff(variableName, inventoryName);
        } else {
            throw new RuntimeException("Statement parse tree with invalid context information");
        }
    }

    @Override
    public Restock visitRestock(WarehouseRobotParser.RestockContext ctx) {

        Num num = new Num(Integer.parseInt(ctx.NUM_PRODUCTS().getText()));


        if (ctx.PRODUCTS_PRODUCT() != null) {
            try {
                Product product = ProductMasterList.getProductGivenName(new Name(ctx.PRODUCTS_PRODUCT().getText()));

                // check that the product is currently assigned to a shelf
                if (product.getProductShelfLocation() == 0) {
                    throw new RuntimeException("Attempting to restock a product that" +
                            " is not currently assigned to a shelf");
                }

                return new Restock(product, num);

            } catch (NoSuchProductException e) {
                throw new RuntimeException(e.getMessage());
            }
        } else {
            throw new RuntimeException("Statement parse tree with invalid context information");
        }


    }

    @Override
    public Fulfill visitFulfill(WarehouseRobotParser.FulfillContext ctx) {
        Name orderName = new Name(ctx.order_varname().CUSTOMER_ORDER().getText());
        Name inventoryName = new Name(ctx.products_varname().RETURN_PRODUCTS().getText());

        return new Fulfill(orderName, inventoryName);
    }

    @Override
    public Add visitAdd(WarehouseRobotParser.AddContext ctx) {
        try {
            Num amount = new Num(Integer.parseInt(ctx.NUM().getText()));
            Product product = ProductMasterList.getProductGivenName(new Name(ctx.PRODUCT().getText()));
            Name inventoryName = new Name(ctx.order_varname().CUSTOMER_ORDER().getText());

            return new Add(amount, product, inventoryName);

        } catch (NoSuchProductException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public CheckOrderAvailability visitCheck_order_availability(WarehouseRobotParser.Check_order_availabilityContext ctx) {
        Name name = new Name(ctx.order_varname().CUSTOMER_ORDER().getText());

        return new CheckOrderAvailability(name);
    }

    @Override
    public CheckProductAvailability visitCheck_product_availability(WarehouseRobotParser.Check_product_availabilityContext ctx) {
        try {
            Num amount = new Num(Integer.parseInt(ctx.NUM().getText()));
            Product product = ProductMasterList.getProductGivenName(new Name(ctx.PRODUCT().getText()));

            return new CheckProductAvailability(product, amount);

        } catch (NoSuchProductException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
