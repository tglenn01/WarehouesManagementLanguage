package src.data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import src.ast.arugments.Num;
import src.ast.arugments.Product;
import src.ast.arugments.locations.Shelf;
import src.model.ProductMasterList;
import src.model.Warehouse;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
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
        JSONArray productArray = new JSONArray();

        productArray.addAll(Arrays.asList(ProductMasterList.VALID_PRODUCTS));

        shelvesObject.put("products", productArray);
    }

    private static void storeShelves(Map<Integer, Shelf> shelves, JSONObject shelvesObject) {
        JSONArray storedShelves = new JSONArray();

        for (Map.Entry<Integer, Shelf> data : shelves.entrySet()) {
            JSONObject storedShelf = new JSONObject();
            Integer location = data.getKey();
            Shelf shelf = data.getValue();

            storeShelfLocation(location, storedShelf);
            storeShelfValidProducts(shelf, storedShelf);
            storeShelfCurrentProducts(shelf, storedShelf);

            storedShelves.add(storedShelf);
        }

        shelvesObject.put("shelves", storedShelves);
    }

    private static void storeShelfLocation(Integer location, JSONObject storedShelf) {
        storedShelf.put("location", Integer.toString(location));
    }

    private static void storeShelfValidProducts(Shelf shelf, JSONObject storedShelf) {
        JSONArray validProductsArray = new JSONArray();
        for (Product product : shelf.getValidProductData()) {
            validProductsArray.add(product.getName());
        }
        storedShelf.put("validProducts", validProductsArray);
    }

    private static void storeShelfCurrentProducts(Shelf shelf, JSONObject storedShelf) {
        JSONArray currentProductArray = new JSONArray();
        Map<Product, Num> products = shelf.getInventoryData();

        products.forEach((product, integer) -> {
            JSONObject productObject = new JSONObject();
            productObject.put("name", product.getName());
            productObject.put("quantity", integer);
            currentProductArray.add(productObject);
        });

        storedShelf.put("inventory", currentProductArray);
    }
}
