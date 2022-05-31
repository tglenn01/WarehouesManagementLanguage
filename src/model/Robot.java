package src.model;

import exceptions.*;
import src.ast.arugments.Product;
import src.ast.arugments.locations.FrontHouse;
import src.ast.arugments.locations.Location;
import src.ast.arugments.locations.Shelf;
import src.ast.orders.CustomerOrder;
import src.ast.orders.FulfilledOrder;

import java.util.HashMap;
import java.util.Map;

public class Robot {
    private Location currentLocation;
    private Map<Product, Integer> currentInventory;

    public Robot() {
        currentLocation = FrontHouse.getInstance();
        currentInventory = new HashMap<>();
    }

    /**
     * Adds product from current shelf to currentInventory with certain amount
     *
     * @param product: The product to be picked up
     * @param amount:  Amount of product to pick up
     * @throws ProductNotValidOnShelfException : throws if product is not stored in current shelf
     * @throws InvalidLocationException        : throws if the robot is told to pickup while at an invalid location
     */
    public void pickup(StringBuilder context, Product product, Integer amount) throws ProductNotValidOnShelfException, InvalidLocationException {
        if (currentLocation == FrontHouse.getInstance()) {
            throw new InvalidLocationException("Robot trying to pick up product while at the Front of House");
        }

        Shelf shelf = (Shelf) currentLocation;

        if (!shelf.isProductValid(product)) {
            throw new ProductNotValidOnShelfException(product, shelf.getWarehouseLocation());
        }

        Map<Product, Integer> pickedUpProductMap;

        pickedUpProductMap = pickUpProductFromShelf(context, product, amount, shelf);
        storeProductsOnRobot(product, pickedUpProductMap);
    }

    private void storeProductsOnRobot(Product product, Map<Product, Integer> pickedUpProductMap) {
        for (Map.Entry<Product, Integer> productEntry : pickedUpProductMap.entrySet()) {
            Product pickedUpProduct = productEntry.getKey();
            Integer pickedUpProductAmount = productEntry.getValue();

            int newAmount;

            if (currentInventory.containsKey(pickedUpProduct)) {
                newAmount = currentInventory.get(pickedUpProduct) + pickedUpProductAmount;
            } else {
                newAmount = pickedUpProductAmount;
            }

            currentInventory.put(product, newAmount);
        }
    }

    private Map<Product, Integer> pickUpProductFromShelf(StringBuilder context, Product product, Integer amount, Shelf shelf) throws ProductNotValidOnShelfException {
        Map<Product, Integer> pickedUpProductMap;
        try {
            pickedUpProductMap = shelf.pickUpProduct(product, amount);
        } catch (InsufficientProductsException e) {
            int amountLeft = shelf.getAmountOfProductLeft(product);
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
     * @throws RobotDoesNotHaveException: throws if robot does not have given product
     */
    public void dropOff(Product product) throws InvalidLocationException, RobotDoesNotHaveException, ProductNotValidOnShelfException {
        if (currentLocation == FrontHouse.getInstance()) {
            throw new InvalidLocationException("Robot trying to drop off product while at the Front of House." +
                    " Are you looking to fulfill order instead?");
        }

        if (!currentInventory.containsKey(product)) {
            throw new RobotDoesNotHaveException(product);
        }

        Shelf shelf = (Shelf) currentLocation;

        if (!shelf.isProductValid(product)) {
            throw new ProductNotValidOnShelfException(product, shelf.getWarehouseLocation());
        }

        shelf.restockProduct(product, currentInventory.get(product));

        currentInventory.remove(product);
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
    public StringBuilder fulfill(CustomerOrder order) throws NotFrontOfHouseException {
        if (this.currentLocation != FrontHouse.getInstance()) {
            throw new NotFrontOfHouseException();
        }


        Map<Product, Integer> fulfillInventory = new HashMap<>();

        for (Map.Entry<Product, Integer> entry : order.getOrderData().entrySet()) {
            Product productNeeded = entry.getKey();
            Integer amountNeeded = entry.getValue();

            if (currentInventory.containsKey(productNeeded)) {
                Integer currentAmountOfProductInInventory = currentInventory.get(productNeeded);

                int amountToAddToFulfillInventory;

                if (currentAmountOfProductInInventory <= amountNeeded) {
                    // if the robot has less than or just enough of the product
                    amountToAddToFulfillInventory = currentAmountOfProductInInventory;
                    currentInventory.remove(productNeeded);
                } else {
                    // if the robot has more than enough
                    amountToAddToFulfillInventory = amountNeeded;
                    currentInventory.put(productNeeded, currentInventory.get(productNeeded) - amountNeeded);
                }

                fulfillInventory.put(productNeeded, amountToAddToFulfillInventory);
            } else {
                fulfillInventory.put(productNeeded, 0);
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
