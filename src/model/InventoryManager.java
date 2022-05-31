package src.model;

import exceptions.ProductNotValidOnShelfException;
import org.json.simple.parser.ParseException;
import src.ast.Product;
import src.ast.locations.Shelf;
import src.data.LoadWarehouse;
import src.data.StoreWarehouse;

import java.io.IOException;
import java.util.Random;

public class InventoryManager {

    public static final String DATA_FILE_LOCATION = "./data/products.json";
    public static Warehouse warehouse;
    public static Robot robot;

    public InventoryManager() {
    }

    public static void execute() throws IOException, ParseException, ProductNotValidOnShelfException {
        WarehouseFactory warehouseFactory = new NormalWarehouseFactory();
        Warehouse warehouse = warehouseFactory.buildWarehouse();
        InventoryManager.warehouse = warehouse;

        LoadWarehouse.loadWarehouse(warehouse, DATA_FILE_LOCATION);

        robot = new Robot();

        StoreWarehouse.storeWarehouse(warehouse, DATA_FILE_LOCATION);
    }

    private static void loadShelvesWithData(Warehouse warehouse) throws ProductNotValidOnShelfException {
        String[] productMasterList = ProductMasterList.VALID_PRODUCTS;
        Random random = new Random();
        int min = 1, max = 9;

        for (int i = 1; i <= 25; i++) {
            Shelf shelf = warehouse.getShelfAtLocation(i);
            Product newProduct = new Product(productMasterList[i], i);
            shelf.addProductToShelf(newProduct);
            shelf.restockProduct(newProduct, random.nextInt(max - min) + min);
        }
    }
}
