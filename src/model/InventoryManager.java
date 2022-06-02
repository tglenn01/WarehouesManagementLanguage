package src.model;

import exceptions.ProductNotValidOnShelfException;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.json.simple.parser.ParseException;
import src.ast.Program;
import src.ast.arugments.Name;
import src.ast.arugments.Num;
import src.ast.arugments.Product;
import src.ast.arugments.locations.Shelf;
import src.ast.evaluators.Evaluator;
import src.data.LoadWarehouse;
import src.data.StoreWarehouse;
import src.parser.ParseTreeToAST;
import src.parser.WarehouseRobotLexer;
import src.parser.WarehouseRobotParser;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class InventoryManager {

    public static final String DATA_FILE_LOCATION = "./data/products.json";
    public static final String INPUT_FILE_LOCATION = "./data/input.txt";
    public static final String OUTPUT_FILE_LOCATION = "./data/output.txt";

    public static Warehouse warehouse;
    public static Robot robot;

    public InventoryManager() {
    }

    public static void execute() throws IOException, ProductNotValidOnShelfException {
        initWarehouse();
        loadShelvesWithData(warehouse);
        initRobot();

        WarehouseRobotLexer lexer = tokenize();
        Program parsedProgram = parse(lexer);
        StringBuilder stringBuilder = evaluate(parsedProgram);

        saveToOutputFile(stringBuilder);
        System.out.println("Output: \n" + stringBuilder);

        saveWarehouse();
    }

    private static void initWarehouse() {
        WarehouseFactory warehouseFactory = new NormalWarehouseFactory();
        InventoryManager.warehouse = warehouseFactory.buildWarehouse();

        // LoadWarehouse.loadWarehouse(warehouse, DATA_FILE_LOCATION);
    }

    private static void initRobot() {
        robot = new Robot();
    }

    private static WarehouseRobotLexer tokenize() throws IOException {
        WarehouseRobotLexer lexer = new WarehouseRobotLexer(CharStreams.fromFileName(INPUT_FILE_LOCATION));
        for (Token token : lexer.getAllTokens()) {
            System.out.println(token);
        }
        lexer.reset();
        System.out.println("Done tokenizing");
        return lexer;
    }

    private static Program parse(WarehouseRobotLexer lexer) {
        TokenStream tokens = new CommonTokenStream(lexer);
        WarehouseRobotParser parser = new WarehouseRobotParser(tokens);
        ParseTreeToAST visitor = new ParseTreeToAST();
        Program parsedProgram = visitor.visitProgram(parser.program());
        System.out.println("Done parsing");
        return parsedProgram;
    }

    private static StringBuilder evaluate(Program parsedProgram) {
        Evaluator evaluator = new Evaluator();
        StringBuilder stringBuilder = new StringBuilder();
        parsedProgram.accept(stringBuilder, evaluator);
        System.out.println("Done evaluating");
        return stringBuilder;
    }

    private static void saveToOutputFile(StringBuilder context) {
        try (FileWriter file = new FileWriter(OUTPUT_FILE_LOCATION)) {
            file.write(context.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void saveWarehouse() throws IOException {
        StoreWarehouse.storeWarehouse(warehouse, DATA_FILE_LOCATION);
    }

    private static void loadShelvesWithData(Warehouse warehouse) throws ProductNotValidOnShelfException {
        String[] productMasterList = ProductMasterList.VALID_PRODUCTS;
        Random random = new Random();
        int min = 1, max = 9;

        for (int i = 1; i <= 25; i++) {
            Shelf shelf = warehouse.getShelfAtLocation(i);
            Product newProduct = new Product(new Name(productMasterList[i]), i);
            shelf.addProductToShelf(newProduct);
            shelf.restockProduct(newProduct, new Num(random.nextInt(max - min) + min));
        }
    }

    public static Warehouse getWarehouse() {
        return warehouse;
    }
}
