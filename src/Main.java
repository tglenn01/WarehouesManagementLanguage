package src;

import exceptions.ProductNotOnShelfException;
import org.json.simple.parser.ParseException;
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

        // LoadWarehouse.loadWarehouse(warehouse, DATA_FILE_LOCATION);


        StoreWarehouse.storeWarehouse(warehouse, DATA_FILE_LOCATION);
    }
}
