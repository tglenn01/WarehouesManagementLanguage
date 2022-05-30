package src;

import exceptions.ProductNotOnShelfException;
import org.json.simple.parser.ParseException;
import src.ast.Product;
import src.ast.locations.Shelf;
import src.data.LoadWarehouse;
import src.data.StoreWarehouse;
import src.model.NormalWarehouseFactory;
import src.model.Warehouse;
import src.model.WarehouseFactory;

import java.io.IOException;

public class Main {
    private static final String DATA_FILE_LOCATION = "./data/products.json";

    public static void main(String[] args) throws IOException, ProductNotOnShelfException, ParseException {
        WarehouseFactory warehouseFactory = new NormalWarehouseFactory();
        Warehouse warehouse = warehouseFactory.buildWarehouse();

        LoadWarehouse.loadWarehouse(warehouse, DATA_FILE_LOCATION);

        Product apple = new Product("Apple", 1);
        Product banana = new Product("Banana", 1);
        Shelf firstShelf = warehouse.getShelfAtLocation(1);
        firstShelf.addProductToShelf(apple);
        firstShelf.addProductToShelf(banana);

        firstShelf.restockProduct(apple, 7);


        StoreWarehouse.storeWarehouse(warehouse, DATA_FILE_LOCATION);
    }
}
