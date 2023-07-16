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
 * Modify Product Screen Application Controller
 * This class is the functionality to modify a product within the Modify Product Screen program.
 */

public class ModifyProductScreenController implements Initializable {

    /**
     * GUI interface element
     */

    @FXML
    private TextField modifyProductIdField;

    /**
     * GUI interface element
     */

    @FXML
    private TextField modifyProductName;

    /**
     * GUI interface element
     */

    @FXML
    private TextField modifyProductInv;

    /**
     * GUI interface element
     */

    @FXML
    private TextField modifyProductPrice;

    /**
     * GUI interface element
     */

    @FXML
    private TextField modifyProductMax;

    /**
     * GUI interface element
     */

    @FXML
    private TextField modifyProductMin;

    /**
     * GUI interface element
     */

    @FXML
    private TableView<Part> addPartToProductTable;

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
    private TableColumn<Part, Integer> inventoryLevelCol;

    /**
     * GUI interface element
     */

    @FXML
    private TableColumn<Part, Double> priceCol;

    /**
     * GUI interface element
     */

    @FXML
    private TextField searchProductTextField;

    /**
     * GUI interface element
     */

    @FXML
    private TableView<Part> deletePartFromProductTable;

    /**
     * GUI interface element
     */

    @FXML
    private TableColumn<Part, Integer> partIDColDelete;

    /**
     * GUI interface element
     */

    @FXML
    private TableColumn<Part, String> partNameColDelete;

    /**
     * GUI interface element
     */

    @FXML
    private TableColumn<Part, Integer> inventoryLevelColDelete;

    /**
     * GUI interface element
     */

    @FXML
    private TableColumn<Part, Double> priceColDelete;


    /**
     * Product object that is selected within the Main Screen Controller
     */

    Product selectedProduct;

    /**
     * List of associated parts within product
     */

    private ObservableList<Part> assocParts = FXCollections.observableArrayList();

    /**
     * This method has a part object added to associated part table from all parts table.
     * Error display message when there is no part selected
     * Confirmation display message when part object is successfully added to associated part table.
     */

    @FXML
    void addPartToProductButton() {
        Part selectedPart = addPartToProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(5);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("SUCCESS");
            alert.setHeaderText("Add successful!");


            if (alert.showAndWait().get() == ButtonType.OK) {
                selectedProduct.addAssociatedPart(selectedPart);
                deletePartFromProductTable.setItems(selectedProduct.getAllAssociatedParts());
            }
        }
    }

    /**
     * This method has a confirmation display message that
     * loads the Main Screen Controller.
     *
     * @param event cancel modify product button action
     * @throws IOException from FXMLLoader
     */

    @FXML
    void cancelModifyProductButton(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setHeaderText("Cancel changes?");
        alert.setContentText("Are you sure you want to cancel and return to main screen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            returnToMainScreen(event);
        }
    }

    /**
     * This method has a confirmation display message and
     * deletes selected associated part(s) from deletePartFromProduct table.
     *
     * Error display message when there is no part selected
     */

    @FXML
    void deletePartFromProductButton() {
        Part selectedPart = deletePartFromProductTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            displayAlert(5);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Removing Part!");
            alert.setContentText("Would you like to remove the part selected?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                assocParts.remove(selectedPart);
                deletePartFromProductTable.setItems(assocParts);
            }
        }
    }

    /**
     * This method replaces the modified product in the
     * inventory and then loads the Main Screen Controller.
     *
     * Validated text fields w/error display message(s)
     * for fields that have invalid values/blank.
     *
     * @param event save modify product button action
     */

    @FXML
    void saveModifyProductButton(ActionEvent event) {
        try {
            int id = selectedProduct.getID();
            String name = modifyProductName.getText();
            Double price = Double.parseDouble(modifyProductPrice.getText());
            int stock = Integer.parseInt(modifyProductInv.getText());
            int min = Integer.parseInt(modifyProductMin.getText());
            int max = Integer.parseInt(modifyProductMax.getText());

            if (name.isEmpty()) {
                displayAlert(7);
            } else {
                if (minValid(min, max) && inventoryValid(min, max, stock)) {
                    Product newProduct = new Product(id, name, price, stock, min, max);

                    for (Part part : assocParts) {
                        newProduct.addAssociatedPart(part);
                    }
                    newProduct.setID(Inventory.getNewPartId());
                    Inventory.addProduct(newProduct);
                    Inventory.deleteProduct(selectedProduct);
                    returnToMainScreen(event);
                }
            }
        } catch (Exception e) {
            displayAlert(1);
        }
    }

    /**
     * This method refills add part to product table with
     * all products when search product txt field is blank.
     */

    @FXML
    void searchModifyProductKeyPressed() {
        if (searchProductTextField.getText().isEmpty()) {
            addPartToProductTable.setItems(Inventory.getAllParts());
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
    void searchProductButton() {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchString = searchProductTextField.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getID()).contains(searchString) || part.getName().contains(searchString)) {
                partsFound.add(part);
            }
        }
        addPartToProductTable.setItems(partsFound);

        if (partsFound.size() == 0) {
            displayAlert(2);
        }
    }

    /**
     * This method loads the Main Screen
     * @param event passed from the parent method
     * @throws IOException from FXMLLoader
     */

    private void returnToMainScreen(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/ViewController/MainScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This confirms that the minimum is greater than zero and less than the maximum.
     * Error display message when minimum doesn't match requirements
     * @param min minimum number value for part
     * @param max maximum number value for part
     * @return confirming if minimum value is valid
     */

    private boolean minValid(int min, int max) {
        boolean isValid = true;
        if (min <= 0 || min >= max) {
            isValid = false;
            displayAlert(3);
        }
        return isValid;
    }

    /**
     * This confirms that the minimum is greater than zero and less than the maximum.
     * Error display message when inventory doesn't match requirements
     * @param min minimum number value for part
     * @param max maximum number value for part
     * @param stock inventory level for part
     * @return confirming if inventory value is vaild
     */

    private boolean inventoryValid(int min, int max, int stock) {
        boolean isValid = true;
        if (stock <= 0 || stock <= min || stock >= max) {
            isValid = false;
            displayAlert(4);
        }
        return isValid;
    }

    /**
     * This method has multiple alert error and information display messages.
     * @param alertType alert message selector
     */

    private void displayAlert(int alertType) {

        Alert alertError = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        switch (alertType) {
            case 1:
                alertError.setTitle("Error");
                alertError.setHeaderText("Modifying Product Error!");
                alertError.setContentText("Blank fields or invalid values!");
                alertError.showAndWait();
                break;
            case 2:
                alertInfo.setTitle("Information");
                alertInfo.setHeaderText("Part Is Not Found!");
                alertInfo.showAndWait();
                break;
            case 3:
                alertError.setTitle("Error");
                alertError.setHeaderText("Invalid Min Value!");
                alertError.setContentText("Min number must be greater than zero and less than Max!");
                alertError.showAndWait();
                break;
            case 4:
                alertError.setTitle("Error");
                alertError.setHeaderText("Invalid Inventory Value!");
                alertError.setContentText("Inventory number must be between Max and Min!");
                alertError.showAndWait();
                break;
            case 5:
                alertError.setTitle("Error");
                alertError.setHeaderText("Part Is Not Selected!");
                alertError.setContentText("Select a part to be removed");
                alertError.showAndWait();
                break;
            case 6:
                alertError.setTitle("Error");
                alertError.setHeaderText("No Name!");
                alertError.setContentText("Name field cannot be blank!");
                alertError.showAndWait();
                break;
        }
    }

    /**
     * This method has a user selected product in the Main Screen displayed
     * in text fields and tables within modify part screen when controller is initialized.
     *
     * @param url relative paths are resolved from the root object's url, or null if url is unknown.
     * @param resourceBundle resources used to localize the root object or null if no localization was done.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedProduct = MainScreenController.getProductToModify();
        assocParts = selectedProduct.getAllAssociatedParts();

        addPartToProductTable.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        addPartToProductTable.refresh();

        deletePartFromProductTable.setItems(assocParts);
        partIDColDelete.setCellValueFactory(new PropertyValueFactory<>("ID"));
        partNameColDelete.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevelColDelete.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColDelete.setCellValueFactory(new PropertyValueFactory<>("price"));
        deletePartFromProductTable.refresh();

        modifyProductIdField.setText(String.valueOf(selectedProduct.getID()));
        modifyProductName.setText(selectedProduct.getName());
        modifyProductInv.setText(String.valueOf(selectedProduct.getStock()));
        modifyProductPrice.setText(String.valueOf(selectedProduct.getPrice()));
        modifyProductMax.setText(String.valueOf(selectedProduct.getMax()));
        modifyProductMin.setText(String.valueOf(selectedProduct.getMin()));

    }
}
