package src.data;

import exceptions.ProductNotValidOnShelfException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import src.ast.arugments.Product;
import src.ast.arugments.locations.Shelf;
import src.model.Warehouse;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class LoadWarehouse {

    // loads all data from the data folder into the warehouse
    public static void loadWarehouse(Warehouse warehouse, String fileLocation) throws IOException, ParseException, ProductNotValidOnShelfException {
        JSONParser parser = new JSONParser();

        Reader reader = new FileReader(fileLocation);

        JSONObject shelvesObject = (JSONObject) parser.parse(reader);

        loadProducts(shelvesObject);
        loadShelves(warehouse, shelvesObject);
    }

    private static void loadProducts(JSONObject shelvesObject) {
        // nothing to do with this data yet
    }

    private static void loadShelves(Warehouse warehouse, JSONObject shelvesObject) throws ProductNotValidOnShelfException {
        JSONArray shelves = (JSONArray) shelvesObject.get("shelves");

        for (Object shelfFromJson : shelves) {
            JSONObject shelfData = (JSONObject) shelfFromJson;

            Integer shelfLocation = Integer.parseInt((String) shelfData.get("location"));
            Shelf warehouseShelf = warehouse.getShelfAtLocation(shelfLocation);

            loadShelfValidProducts(shelfLocation, shelfData, warehouseShelf);
            loadShelfInventory(shelfLocation, shelfData, warehouseShelf);
        }
    }

    private static void loadShelfValidProducts(Integer shelfLocation, JSONObject shelfData, Shelf warehouseShelf) {
        List<String> validShelfProducts = (List<String>) shelfData.get("validProducts");
        for (String name : validShelfProducts) {
            warehouseShelf.addProductToShelf(new Product(name, shelfLocation));
        }
    }

    private static void loadShelfInventory(Integer shelfLocation, JSONObject shelfData, Shelf warehouseShelf) throws ProductNotValidOnShelfException {
        JSONArray shelfInventory = (JSONArray) shelfData.get("inventory");

        for (Object productInShelfInventory : shelfInventory) {
            JSONObject shelfProduct = (JSONObject) productInShelfInventory;

            String productName = (String) shelfProduct.get("name");
            Integer productQuantity = ((Long) shelfProduct.get("quantity")).intValue();

            Product createdProduct = new Product(productName, shelfLocation);

            warehouseShelf.restockProduct(createdProduct, productQuantity);
        }
    }
}
