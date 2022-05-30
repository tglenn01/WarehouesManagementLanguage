package src.data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import src.ast.Product;
import src.ast.locations.Shelf;
import src.model.Warehouse;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class StoreWarehouse {

    // gets all data from the warehouse and saves it in the data folder
    public static void storeWarehouse(Warehouse warehouse, String fileLocation) throws IOException {
        Map<Integer, Shelf> shelves = warehouse.getShelvesData();

        JSONObject shelvesObject = new JSONObject();

        storeListOfProducts(shelvesObject);
        storeShelves(shelves, shelvesObject);

        try (FileWriter file = new FileWriter(fileLocation)) {
            file.write(shelvesObject.toJSONString());
        }
    }

    private static void storeListOfProducts(JSONObject shelvesObject) {
        JSONArray products = new JSONArray();
        products.addAll(List.of(Product.VALID_PRODUCTS));
        shelvesObject.put("products", products);
    }

    private static void storeShelves(Map<Integer, Shelf> shelves, JSONObject shelvesObject) {
        JSONArray storedShelves = new JSONArray();

        shelves.forEach((location, shelf) -> {
            JSONArray storedShelf = new JSONArray();

            storeShelfLocation(location, storedShelf);
            storeShelfValidProducts(shelf, storedShelf);
            storeShelfCurrentProducts(shelf, storedShelf);

        });

        shelvesObject.put("shelves", storedShelves);
    }

    private static void storeShelfLocation(Integer location, JSONArray storedShelf) {
        JSONObject locationObject = new JSONObject();
        locationObject.put("location", location);
        storedShelf.add(locationObject);
    }

    private static void storeShelfValidProducts(Shelf shelf, JSONArray storedShelf) {
        JSONArray validProductsArray = new JSONArray();
        for (Product product : shelf.getValidProductData()) {
            validProductsArray.add(product.getName());
        }
        storedShelf.add(validProductsArray);
    }

    private static void storeShelfCurrentProducts(Shelf shelf, JSONArray storedShelf) {
        JSONArray currentProductArray = new JSONArray();
        Map<Product, Integer> products = shelf.getProductData();

        products.forEach((product, integer) -> {
            JSONObject productObject = new JSONObject();
            productObject.put("name", product.getName());
            productObject.put("quantity", integer);
            currentProductArray.add(productObject);
        });

        JSONObject currentInventory = new JSONObject();
        currentInventory.put("inventory", currentProductArray);

        storedShelf.add(currentInventory);
    }


}
