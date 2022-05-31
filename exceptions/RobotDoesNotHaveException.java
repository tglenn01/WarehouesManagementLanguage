package exceptions;

import src.ast.arugments.Product;

public class RobotDoesNotHaveException extends Exception {

    public RobotDoesNotHaveException(Product product) {
        super("Robot does not have " + product.getName() + " in it's inventory currently");
    }
}
