package src;

import exceptions.ProductNotValidOnShelfException;
import org.json.simple.parser.ParseException;
import src.model.InventoryManager;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ProductNotValidOnShelfException, ParseException {
        InventoryManager.execute();
    }
}
