package exceptions;

import src.ast.arugments.Product;

public class RobotDoesNotHaveProductException extends Exception {

    public RobotDoesNotHaveProductException(Product product) {
        super("Robot does not have " + product.getName() + " in it's inventory currently");
    }

}
