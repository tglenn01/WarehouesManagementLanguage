package exceptions;

import src.ast.arugments.Product;

public class ProductNotValidOnShelfException extends Exception {

    public ProductNotValidOnShelfException(Product product, Integer shelfLocation) {
        super("Products: " + product.getName() + " is not on shelf " + shelfLocation);
    }
}
