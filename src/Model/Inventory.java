package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Nyssa Robinson
 * Inventory Model contains part and product information
 * This class defines part and product information
 */

public class Inventory {

    /**
     * This method list all parts
     */

    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();


    /**
     * This method retrieves all parts list
     * @return part list
     */

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * This method list all products
     */

    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * This method retrieves all products list
     * @return product list
     */

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * This method adds a new part to Main Screen
     * @param newPart part that is added
     */

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * This method adds a new product to Main Screen
     * @param newProduct product that is added
     */

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * This method is a part ID that helps to produce new partID
     */

    private static int partId = 0;

    /**
     * This method produces a new partID
     * @return new partID
     */

    public static int getNewPartId() {return ++partId;}

    /**
     * This method is a product ID that helps to produce new productID
     */

    private static int productId = 0;

    /**
     * This method produces a new productID
     * @return new productID
     */

    public static int getNewProduct() {return ++productId;
    }

    /**
     * This methods deletes part(s) from table
     * @param selectedPart deleted part
     * @return part is deleted
     */

    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * This methods deletes product(s) from table
     * @param selectedProduct deleted product
     * @return product is deleted
     */

    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }
}
