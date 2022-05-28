package src.ast;

import exceptions.*;
import src.ast.locations.FrontHouse;
import src.ast.locations.Location;
import src.ast.orders.CustomerOrder;

import java.util.Map;

public class Robot extends Node {
    private Location currentLocation;
    private Map<Product, Integer> currentInventory;

    public Robot() {
        currentLocation = FrontHouse.getInstance();
    }

    /**
     *
     *   Adds product from current shelf to currentInventory with certain amount
     *
     *   @param product: The product to be picked up
     *   @param amount: Amount of product to pick up
     *
     *   @throws InsufficientProductsException: if product is in current location but there is not enough
     *   @throws ProductNotOnShelfException: if product is not stored in current location
     *
     * */
    public void pickup(Product product, Integer amount) throws InsufficientProductsException, ProductNotOnShelfException {
        // stub
    }


    /**
     *
     * drops of all the given products at location
     *
     * @param product: product to drop off
     * @throws InvalidLocationException: throws if dropping off item at invalid location
     * @throws RobotDoesNotHaveException: throws if robot does not have given product
     *
     */
    public void dropOff(Product product) throws InvalidLocationException, RobotDoesNotHaveException{
        // stub
    }


    /**
     *
     * Moves the robot from its current location to the new location
     *
     * @param location: Location for the Robot to move
     * @throws InvalidLocationException: throws if robot is given an invalid Location
     *
     */
    public void goTo(Location location) throws InvalidLocationException {
        // stub
    }


    /**
     *
     * Creates the offical fulfilled order and sends the front of house the fulfilled order
     *
     * @param order: The customer order that needs to be fulfilled
     * @throws NotFrontOfHouseException: throws if robot is not currently at the front of house
     *
     */
    public void fulfill(CustomerOrder order) throws NotFrontOfHouseException {
        // stub
    }

    // returns robots current location
    public Location getRobotLocation() {
        return currentLocation;
    }
}
