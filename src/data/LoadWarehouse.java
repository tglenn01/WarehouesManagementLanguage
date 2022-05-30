package src.data;

import exceptions.ProductNotOnShelfException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import src.ast.Product;
import src.ast.locations.Shelf;
import src.model.Warehouse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class LoadWarehouse {

    // loads all data from the data folder into the warehouse
    public static void loadWarehouse(Warehouse warehouse, String fileLocation) throws IOException, ParseException, ProductNotOnShelfException {
        JSONParser parser = new JSONParser();

        Reader reader = new FileReader(fileLocation);

        JSONObject shelvesObject = (JSONObject) parser.parse(reader);

        JSONArray validProducts = (JSONArray) shelvesObject.get("products");

        JSONArray shelves = (JSONArray) shelvesObject.get("shelves");

        for (Object shelfFromJson : shelves) {
            JSONObject shelfData = (JSONObject) shelfFromJson;

            Integer shelfLocation = (Integer) shelfData.get("location");
            Shelf warehouseShelf = warehouse.getShelfAtLocation(shelfLocation);

            loadShelfValidProducts(shelfData, warehouseShelf);
            loadShelfInventory(shelfLocation, shelfData, warehouseShelf);
        }
    }

    private static void loadShelfValidProducts(JSONObject shelfData, Shelf warehouseShelf) {
        List<Product> validShelfProducts = (List<Product>) shelfData.get("validProducts");
        for (Product product : validShelfProducts) {
            warehouseShelf.addProductToShelf(product);
        }
    }

    private static void loadShelfInventory(Integer shelfLocation, JSONObject shelfData, Shelf warehouseShelf) throws ProductNotOnShelfException {
        JSONArray shelfInventory = (JSONArray) shelfData.get("inventory");

        for (Object productInShelfInventory : shelfInventory) {
            JSONObject shelfProduct = (JSONObject) productInShelfInventory;

            String productName = (String) shelfProduct.get("name");
            Integer productQuantity = (Integer) shelfProduct.get("quantity");

            Product createdProduct = new Product(productName, shelfLocation);

            warehouseShelf.restockProduct(createdProduct, productQuantity);
        }
    }
}
