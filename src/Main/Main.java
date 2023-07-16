package Main;

import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Nyssa Robinson
 *
 * "RUNTIME ERROR" The int productId iPhone and AirPods would initally automatically
 * populate the assosciated parts tables with all parts list.
 * I noticed I didn't use the ObserverList of assocParts via
 * observerArrayList to correctly populate the table.
 * To fix this issue I set items of the assocParts to the deletePartFromProductTable via ModifyProductScreenController,
 * line 433. Now only user selected associated parts are added to the deletePartFromProductTable.
 *
 * "FUTURE ENHANCEMENT" The inventory management system would benefit from a reorder
 * point where the minimum inventory level initiates a reorder when stock becomes low.
 * Reordering guarantee we don't run out of a particular part/product.
 *
 * Main class initializes the Main Screen for inventory management
 */

public class Main extends Application {

    /**
     * This method prints process starting within terminal when program initially runs
     */

    @Override
    public void init() {
        System.out.println("Process starting");
    }

    /**
     * This method generates the FXML stage that load the Main Screen
     * @param primaryStage main screen
     * @throws Exception from FXMLLoader
     */

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../ViewController/MainScreen.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    /**
     * This is the main method that is the entry point of the Main Screen.
     * Also includes test parts and products
     * @param args String array that receive parameters for inventory input
     */

    //JavaDoc files location>> file:///C:/Users/Nyssa%20Robinson/IdeaProjects/NR%20Inventory/JavaDoc/index.html
    public static void main(String[] args) {

        int partId = Inventory.getNewPartId();
        OutsourcedPart siliconeCase = new OutsourcedPart(partId,"Silicone Case", 59.00, 100, 1, 300, "Apple");
        Inventory.addPart(siliconeCase);

        partId = Inventory.getNewPartId();
        InhousePart chargerCable = new InhousePart(partId,"Charger Cable",49.00 , 200, 1, 300, 102);
        Inventory.addPart(chargerCable);

        partId = Inventory.getNewPartId();
        InhousePart powerCable = new InhousePart(partId,"Power Adapter", 19.99, 250, 1, 300, 103);
        Inventory.addPart(powerCable);

        partId = Inventory.getNewPartId();
        OutsourcedPart batteryPack = new OutsourcedPart(partId,"Battery Pack", 109.00, 150, 1, 300, "Apple");
        Inventory.addPart(batteryPack);

        int productId = Inventory.getNewProduct();
        Product iPhone = new Product(productId,"iPhone 14 Pro", 1099.99, 15, 1, 30);
        Inventory.addProduct(iPhone);

        productId = Inventory.getNewProduct();
        Product airPods = new Product(productId,"AirPods", 19.99, 135, 1, 200);
        Inventory.addProduct(airPods);

        launch(args);
    }
}
