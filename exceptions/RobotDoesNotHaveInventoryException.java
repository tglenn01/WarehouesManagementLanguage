package exceptions;

public class RobotDoesNotHaveInventoryException extends Exception {

    public RobotDoesNotHaveInventoryException(String varName) {
        super("Trying to pickup to an inventory that does not exist: " + varName);
    }
}
