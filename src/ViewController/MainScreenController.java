package ViewController;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Nyssa Robinson
 * Main Screen Application Controller initializes the UI
 * This class is the main controller that allows control logic for the Main Screen to navigate to other
 *  controllers and populate table views.
 */

public class MainScreenController implements Initializable {

    /**
     * GUI interface element
     */

    @FXML
    private TextField partSearchTxt;

    /**
     * GUI interface element
     */

    @FXML
    private TableView<Part> partsTable;

    /**
     * GUI interface element
     */

    @FXML
    private TableColumn<Part, Integer> partIDCol;

    /**
     * GUI interface element
     */

    @FXML
    private TableColumn<Part, String> partNameCol;

    /**
     * GUI interface element
     */

    @FXML
    private TableColumn<Part, Integer> partInvLevelCol;

    /**
     * GUI interface element
     */

    @FXML
    private TableColumn<Part, Double> partCostCol;

    /**
     * GUI interface element
     */

    @FXML
    private TextField productSearchTxt;

    /**
     * GUI interface element
     */

    @FXML
    private TableView<Product> productTable;

    /**
     * GUI interface element
     */

    @FXML
    private TableColumn<Product, Integer> productIDCol;

    /**
     * GUI interface element
     */

    @FXML
    private TableColumn<Product, String> productNameCol;

    /**
     * GUI interface element
     */

    @FXML
    private TableColumn<Product, Integer> productInvLevelCol;

    /**
     * GUI interface element
     */

    @FXML
    private TableColumn<Product, Double> productCostCol;

    /**
     * This method is the user selected part object form
     * within part table from Main Screen.
     */

    private static Part partToModify;

    /**
     * This method retrieves the user selected part object
     * form within part table from Main Screen.
     */

    public static Part getPartToModify() {
        return partToModify;
    }

    /**
     * This method is the user selected product object form
     * within product table from Main Screen.
     */

    private static Product productToModify;

    /**
     * This method retrieves the user selected product object
     * form within product table from Main Screen.
     */

    public static Product getProductToModify() {
        return productToModify;
    }

    /**
     * This method exits the program via confirmation display
     * message when OK is selected.
     */

    @FXML
    void exitButton() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exiting");
        alert.setHeaderText("You are about to exit!");
        alert.setContentText("Do you wish to exit?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    /**
     * This method loads add part screen
     * @param event part add button action
     * @throws IOException from FXMLLoader
     */

    @FXML
    void partAddButton(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/ViewController/AddPartScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method deletes user selected part from part table on Main Screen
     * via confirmation display message when OK is selected.
     */

    @FXML
    void partDeleteButton() {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            displayAlert(3);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete Part");
            alert.setContentText("Are you sure you want to delete this part?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            }
        }
    }

    /**
     * This method loads modify part screen and error
     * display message when part is not selected.
     * @param event part modify button action
     * @throws IOException from FXMLLoader
     */

    @FXML
    void partModifyButton(ActionEvent event) throws IOException {

        partToModify = partsTable.getSelectionModel().getSelectedItem();
        if (partToModify == null) {
            displayAlert(3);
        } else {
            Parent parent = FXMLLoader.load(getClass().getResource("/ViewController/ModifyPartScreen.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * This method launches part search according to the value
     * within the part search txt field. Then refreshes
     * parts table w/searched results.
     * Information display message when part is not found.
     * Search parts by name or ID.
     */

    @FXML
    void partSearchButton() {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchString = partSearchTxt.getText();
        for (Part part : allParts) {
            if (String.valueOf(part.getID()).contains(searchString) || part.getName().contains(searchString)) {
                partsFound.add(part);
            }
        }
        partsTable.setItems(partsFound);

        if (partsFound.size() == 0) {
            displayAlert(1);
        }
    }

    /**
     * This method refills parts table with
     * all parts when search product txt field is blank.
     */

    @FXML
    public void partSearchTxtKeyPressed() {
        if (partSearchTxt.getText().isEmpty()) {
            partsTable.setItems(Inventory.getAllParts());
        }
    }

    /**
     * This method loads add product screen
     * @param event product add button action
     * @throws IOException from FXMLLoader
     */

    @FXML
    void productAddButton(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/ViewController/AddProductScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method deletes user selected product from product table on Main Screen
     * via confirmation display message when OK is selected.
     */

    @FXML
    void productDeleteButton() {

        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            displayAlert(4);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Deleting Product!");
            alert.setContentText("Are you sure you want to delete this product?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                ObservableList<Part> assocParts = selectedProduct.getAllAssociatedParts();
                if (assocParts.size() >= 1) {
                    displayAlert(5);
                } else {
                    Inventory.deleteProduct(selectedProduct);
                }
            }
        }
    }

    /**
     * This method loads modify product screen and confirmation
     * display message when product is not selected.
     * @param event product modify button action
     * @throws IOException from FXMLLoader
     */

    @FXML
    void productModifyButton(ActionEvent event) throws IOException {
        productToModify = productTable.getSelectionModel().getSelectedItem();
        if (productToModify == null) {
            displayAlert(4);
        } else {
            Parent parent = FXMLLoader.load(getClass().getResource("/ViewController/ModifyProductScreen.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * This method launches part search according to the value
     * within the search product text field. Then refreshes add
     * partToProduct table w/searched results.
     * Information display message when part is not found.
     * Search parts by name or ID
     */

    @FXML
    void productSearchButton() {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> productsFound = FXCollections.observableArrayList();
        String searchString = productSearchTxt.getText();
        for (Product product : allProducts) {
            if (String.valueOf(product.getID()).contains(searchString) || product.getName().contains(searchString)) {
                productsFound.add(product);
            }
        }
        productTable.setItems(productsFound);

        if (productsFound.size() == 0) {
            displayAlert(2);
        }
    }

    /**
     * /**
     * This method refills product table with
     * all products when search product txt field is blank.
     */

    @FXML
    public void productSearchTxtKeyPressed() {
        if (productSearchTxt.getText().isEmpty()) {
            productTable.setItems(Inventory.getAllProducts());
        }
    }

    /**
     * This method has multiple alert error and information display messages.
     * @param alertType alert message selector
     */

    private void displayAlert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alert.setTitle("Information");
                alert.setHeaderText("Part not found");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Information");
                alert.setHeaderText("Product not found");
                alert.showAndWait();
                break;
            case 3:
                alertError.setTitle("Error");
                alertError.setHeaderText("Part not selected");
                alertError.showAndWait();
                break;
            case 4:
                alertError.setTitle("Error");
                alertError.setHeaderText("Product not selected");
                alertError.showAndWait();
                break;
            case 5:
                alertError.setTitle("Error");
                alertError.setHeaderText("Part Associated");
                alertError.setContentText("All parts must be removed from product before deleting!");
                alertError.showAndWait();
                break;
        }
    }

    /**
     * This method populates the parts table and product table within Main Screen when controller is initialized.
     *
     * @param location relative paths are resolved from the root object's location, or null if location is unknown.
     * @param resources used to localize the root object or null if no localization was done.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        partsTable.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTable.refresh();

        productTable.setItems(Inventory.getAllProducts());
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productTable.refresh();
    }
}