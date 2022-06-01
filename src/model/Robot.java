package src.model;

import exceptions.*;
import src.ast.arugments.Name;
import src.ast.arugments.Num;
import src.ast.arugments.Product;
import src.ast.arugments.locations.FrontHouse;
import src.ast.arugments.locations.Location;
import src.ast.arugments.locations.Shelf;
import src.ast.arugments.orders.CustomerOrder;
import src.ast.arugments.orders.FulfilledOrder;

import java.util.HashMap;
import java.util.Map;

public class Robot {
    private Location currentLocation;
    private final Map<Name, Inventory> currentInventories;

    public Robot() {
        currentLocation = FrontHouse.getInstance();
        currentInventories = new HashMap<>();
    }

    /**
     * Adds product from current shelf to currentInventory with certain amount
     *
     * @param product: The product to be picked up
     * @param amount:  Amount of product to pick up
     * @throws ProductNotValidOnShelfException : throws if product is not stored in current shelf
     * @throws InvalidLocationException        : throws if the robot is told to pickup while at an invalid location
     */
    public void pickup(StringBuilder context, Product product, Num amount, Name inventoryName) throws ProductNotValidOnShelfException, InvalidLocationException, RobotDoesNotHaveInventoryException {
        if (currentLocation == FrontHouse.getInstance()) {
            throw new InvalidLocationException("Robot trying to pick up product while at the Front of House");
        }

        if (!currentInventories.containsKey(inventoryName)) {
            throw new RobotDoesNotHaveInventoryException(inventoryName.name);
        }

        Shelf shelf = (Shelf) currentLocation;

        if (!shelf.isProductValid(product)) {
            throw new ProductNotValidOnShelfException(product, shelf.getWarehouseLocation());
        }

        Inventory pickedUpProductMap;

        pickedUpProductMap = pickUpProductFromShelf(context, product, amount, shelf);
        storeProductsOnRobot(product, pickedUpProductMap, inventoryName);
    }

    private void storeProductsOnRobot(Product product, Inventory pickedUpProductMap, Name inventoryName) {
        Inventory workingInventory = currentInventories.get(inventoryName);

        for (Map.Entry<Product, Num> productEntry : pickedUpProductMap.entrySet()) {
            Product pickedUpProduct = productEntry.getKey();
            Num pickedUpProductAmount = productEntry.getValue();

            Num newAmount;

            if (workingInventory.containsKey(pickedUpProduct)) {
                newAmount = Num.add(workingInventory.get(pickedUpProduct), pickedUpProductAmount);
            } else {
                newAmount = pickedUpProductAmount;
            }

            workingInventory.put(product, newAmount);
        }
    }

    private Inventory pickUpProductFromShelf(StringBuilder context, Product product, Num amount, Shelf shelf) throws ProductNotValidOnShelfException {
        Inventory pickedUpProductMap;
        try {
            pickedUpProductMap = shelf.pickUpProduct(product, amount);
        } catch (InsufficientProductsException e) {
            Num amountLeft = shelf.getAmountOfProductLeft(product);
            context.append("There was not enough ")
                    .append(product.getName())
                    .append(" at shelf ")
                    .append(shelf.getWarehouseLocation())
                    .append(" getting all ")
                    .append(amountLeft)
                    .append("  left instead");
            try {
                pickedUpProductMap = shelf.pickUpProduct(product, amountLeft);
            } catch (InsufficientProductsException ex) {
                throw new RuntimeException("Error with robot picking up a product," +
                        " that it is asking for more than the shelf has");
            }
        }
        return pickedUpProductMap;
    }


    /**
     * drops of all the given products at location
     *
     * @param product: product to drop off
     * @throws InvalidLocationException:  throws if dropping off item at invalid location
     * @throws RobotDoesNotHaveProductException : throws if robot does not have given product
     */
    public void dropOff(Product product, Name inventoryToTakeFrom) throws InvalidLocationException, RobotDoesNotHaveProductException, ProductNotValidOnShelfException, RobotDoesNotHaveInventoryException {
        if (!currentInventories.containsKey(inventoryToTakeFrom)) {
            throw new RobotDoesNotHaveInventoryException(inventoryToTakeFrom.name);
        }

        if (currentLocation == FrontHouse.getInstance()) {
            throw new InvalidLocationException("Robot trying to drop off product while at the Front of House." +
                    " Are you looking to fulfill order instead?");
        }

        Inventory workingInventory = currentInventories.get(inventoryToTakeFrom);

        if (workingInventory.containsKey(product)) {
            throw new RobotDoesNotHaveProductException(product);
        }

        Shelf shelf = (Shelf) currentLocation;

        if (!shelf.isProductValid(product)) {
            throw new ProductNotValidOnShelfException(product, shelf.getWarehouseLocation());
        }

        shelf.restockProduct(product, workingInventory.get(product));

        workingInventory.remove(product);
    }


    /**
     * Moves the robot from its current location to the new location
     *
     * @param location: Location for the Robot to move
     */
    public void goTo(Location location) {
        this.currentLocation = location;
    }


    /**
     * Creates the offical fulfilled order and sends the front of house the fulfilled order
     *
     * @param order: The customer order that needs to be fulfilled
     * @throws NotFrontOfHouseException: throws if robot is not currently at the front of house
     */
    public StringBuilder fulfill(CustomerOrder order, Name inventoryName) throws NotFrontOfHouseException, RobotDoesNotHaveInventoryException {
        if (this.currentLocation != FrontHouse.getInstance()) {
            throw new NotFrontOfHouseException();
        }

        if (!currentInventories.containsKey(inventoryName)) {
            throw new RobotDoesNotHaveInventoryException(inventoryName.name);
        }

        Inventory workingInventory = currentInventories.get(inventoryName);

        Inventory fulfillInventory = new Inventory();

        for (Map.Entry<Product, Num> entry : order.getOrderData().entrySet()) {
            Product productNeeded = entry.getKey();
            Num amountNeeded = entry.getValue();

            if (workingInventory.containsKey(productNeeded)) {
                Num currentAmountOfProductInInventory = workingInventory.get(productNeeded);

                Num amountToAddToFulfillInventory;

                if (currentAmountOfProductInInventory.number <= amountNeeded.number) {
                    // if the robot has less than or just enough of the product
                    amountToAddToFulfillInventory = currentAmountOfProductInInventory;
                    workingInventory.remove(productNeeded);
                } else {
                    // if the robot has more than enough
                    amountToAddToFulfillInventory = amountNeeded;
                    workingInventory.put(productNeeded, Num.subtract(workingInventory.get(productNeeded), amountNeeded));
                }

                fulfillInventory.put(productNeeded, amountToAddToFulfillInventory);
            } else {
                fulfillInventory.put(productNeeded, new Num(0));
            }
        }


        FulfilledOrder fulfilledOrder = new FulfilledOrder(fulfillInventory);

        return FrontHouse.getInstance().fulfill(order, fulfilledOrder);
    }

    // returns robots current location
    public Location getRobotLocation() {
        return currentLocation;
    }
}
