package exceptions;

public class NoSuchProductException extends Exception {

    public NoSuchProductException(String name) {
        super("No such product with name " + name);
    }
}
