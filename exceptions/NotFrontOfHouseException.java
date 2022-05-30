package exceptions;

public class NotFrontOfHouseException extends Exception {

    public NotFrontOfHouseException() {
        super("Robot was not at the front house when it was expected to be");
    }
}
