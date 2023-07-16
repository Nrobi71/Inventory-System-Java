package ViewController;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Nyssa Robinson
 * Modify Part Screen Application Controller
 * This class is the functionality to modify a part within the Modify Part Screen.
 */

public class ModifyPartScreenController implements Initializable {

    /**
     * GUI interface element
     */

    @FXML
    private RadioButton inHouseRadioButton;

    /**
     * GUI interface element
     */

    @FXML
    private RadioButton outsourcedRadioButton;

    /**
     * GUI interface element
     */

    @FXML
    private TextField modifyPartIdField;

    /**
     * GUI interface element
     */

    @FXML
    private TextField modifyPartName;

    /**
     * GUI interface element
     */

    @FXML
    private TextField modifyPartInv;

    /**
     * GUI interface element
     */

    @FXML
    private TextField modifyPartPrice;

    /**
     * GUI interface element
     */

    @FXML
    private TextField modifyPartDynamicField;

    /**
     * GUI interface element
     */

    @FXML
    private TextField modifyPartMax;

    /**
     * GUI interface element
     */

    @FXML
    private TextField ModifyPartMin;

    /**
     * GUI interface element
     */

    @FXML
    private Label machineID;

    /**
     * Part object that is selected within the Main Screen Controller
     */

    private Part selectedPart;

    /**
     * This method has a confirmation display message that
     * loads the Main Screen Controller.
     *
     * @param event cancel modify part button action
     * @throws IOException from FXMLLoader
     */

    @FXML
    void cancelModifyPartButton(ActionEvent event) throws IOException {
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
     * This method sets the machine ID label to "Machine ID"
     */

    @FXML
    void inhouseRadioButtonSelected() {

        machineID.setText("Machine ID");
    }

    /**
     * This method sets the machine ID label to "Company Name"
     */

    @FXML
    void outsourcedRadioButtonSelected() {

        machineID.setText("Company Name");
    }

    /**
     * This method replaces the modified product in the
     * inventory and then loads the Main Screen Controller.
     *
     * Validated text fields w/error display message(s)
     * for fields that have invalid values/blank.
     *
     * @param event save modify part button action
     */

    @FXML
    void saveModifyPartButton(ActionEvent event) {
        try {
            int id = selectedPart.getID();
            String name = modifyPartName.getText();
            Double price = Double.parseDouble(modifyPartPrice.getText());
            int stock = Integer.parseInt(modifyPartInv.getText());
            int min = Integer.parseInt(ModifyPartMin.getText());
            int max = Integer.parseInt(modifyPartMax.getText());
            int machineId;
            String companyName;
            boolean partAddSuccessful = false;

            if (minValid(min, max) && inventoryValid(min, max, stock)) {

                if (inHouseRadioButton.isSelected()) {
                    try {
                        machineId = Integer.parseInt(modifyPartDynamicField.getText());
                        InhousePart newInhousePart = new InhousePart(id, name, price, stock, min, max, machineId);
                        newInhousePart.setID(Inventory.getNewPartId());
                        Inventory.addPart(newInhousePart);
                        partAddSuccessful = true;
                    } catch (Exception e) {
                        displayAlert(2);
                    }
                }
                if (outsourcedRadioButton.isSelected()) {
                    companyName = modifyPartDynamicField.getText();
                    OutsourcedPart newOutsourcedPart = new OutsourcedPart(id, name, price, stock, min, max, companyName);
                    newOutsourcedPart.setID(Inventory.getNewPartId());
                    Inventory.addPart(newOutsourcedPart);
                    partAddSuccessful = true;
                }
                if (partAddSuccessful) {

                    Inventory.deletePart(selectedPart);
                    returnToMainScreen(event);
                }
            }
        } catch (Exception e) {
            displayAlert(1);
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

        if (min <= 0 || min >+ max) {
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

        if (stock <= min || stock >+ max) {
            isValid = false;
            displayAlert(3);
        }
        return isValid;
    }

    /**
     * This method has multiple alert error and information display messages.
     * @param alertType alert message selector
     */

    private void displayAlert(int alertType) {
        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alertError.setTitle("Error");
                alertError.setHeaderText("Modifying Part Error!");
                alertError.setContentText("Blank fields or invalid values!");
                alertError.showAndWait();
                break;
            case 2:
                alertError.setTitle("Error");
                alertError.setHeaderText("Invalid Machine ID Value");
                alertError.setContentText("Machine ID must contain only numbers!");
                alertError.showAndWait();
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
        }
    }

    /**
     * This method has a user selected product in the Main Screen
     * displayed in text fields and tables within modify part screen when controller is initialized.
     * @param url relative paths are resolved from the root object's url, or null if url is unknown.
     * @param resourceBundle resources used to localize the root object or null if no localization was done.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectedPart = MainScreenController.getPartToModify();

        if (selectedPart instanceof InhousePart) {
            inHouseRadioButton.setSelected(true);
            machineID.setText("Machine ID");
            modifyPartDynamicField.setText(String.valueOf(((InhousePart) selectedPart).getMachineID()));
        }
        if (selectedPart instanceof OutsourcedPart) {
            outsourcedRadioButton.setSelected(true);
            machineID.setText("Company Name");
            modifyPartDynamicField.setText(((OutsourcedPart) selectedPart).getCompanyName());
        }
        modifyPartIdField.setText(String.valueOf(selectedPart.getID()));
        modifyPartName.setText(selectedPart.getName());
        modifyPartInv.setText(String.valueOf(selectedPart.getStock()));
        modifyPartPrice.setText(String.valueOf(selectedPart.getPrice()));
        modifyPartMax.setText(String.valueOf(selectedPart.getMax()));
        ModifyPartMin.setText(String.valueOf(selectedPart.getMin()));
    }

}
