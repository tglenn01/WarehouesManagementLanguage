package src;

import exceptions.ProductNotValidOnShelfException;
import org.json.simple.parser.ParseException;
import src.ast.Product;
import src.ast.locations.Shelf;
import src.data.LoadWarehouse;
import src.data.StoreWarehouse;
import src.model.*;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ProductNotValidOnShelfException, ParseException {
        InventoryManager.execute();
    }
}
