package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Nyssa Robinson
 * Product model contains product information that may also have assosciated parts
 * This class defines the products
 */

public class Product {

    /**
     * This method is split into declaration and initialization that list all associated parts
     */

    private final ObservableList<Part> associatedParts;

    {
        associatedParts = FXCollections.observableArrayList();
    }

    /**
     * This method retrieves product associated parts list
     * @return part list
     */

    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**
     * These methods are the product's ID, number of products in stock,
     * minimum number of products, maximum number of products, name, and price
     */

    private int ID;
    private final int stock;
    private int min;
    private int max;
    private String name;
    private double price;

    /**
     * This is a constructor
     * @param ID product ID
     * @param name product name
     * @param price product price
     * @param stock inventory stock
     * @param min minimum number of products in stock
     * @param max maximum number of products in stock
     */

    public Product(int ID, String name, double price, int stock, int min, int max) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * This method is ID setter
     * @param ID product ID
     */

    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * This method is ID getter
     * @return product ID
     */

    public int getID() {
        return ID;
    }

    /**
     * This method is Name setter
     * @param name product Name
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method is Name getter
     * @return product Name
     */

    public String getName() {
        return name;
    }

    /**
     * This method is Price setter
     * @param price product price
     */

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * This method is Price getter
     * @return product Price
     */

    public double getPrice() {
        return price;
    }

    /**
     * This method is Stock getter
     * @return product inventory stock
     */

    public int getStock() {
        return stock;
    }

    /**
     * This method is Minimum setter
     * @param min minimum number of product
     */

    public void setMin(int min) {
        this.min = min;
    }

    /**
     * This method is Minimum getter
     * @return minimum number of product
     */

    public int getMin() {
        return min;
    }

    /**
     * This method is Maximum setter
     * @param max maximum number of product
     */

    public void setMax(int max) {
        this.max = max;
    }

    /**
     * This method is Maximum getter
     * @return max maximum number of product
     */

    public int getMax() {
        return max;
    }

    /**
     * This methods add user selected part(s) to product
     * @param part added part
     */

    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

}
