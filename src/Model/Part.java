package Model;

/**
 * @author Nyssa Robinson
 * Product model contains part information
 * This class defines the parts
 */

public abstract class Part {

    /**
     * These methods are the product's ID, number of parts in stock,
     * minimum number of parts, maximum number of parts, name, and price
     */

    private int ID;
    private final int stock;
    private int min;
    private int max;
    private String name;
    private double price;

    /**
     * This is a constructor
     * @param ID part ID
     * @param name part name
     * @param price part price
     * @param stock inventory stock
     * @param min minimum number of part in stock
     * @param max maximum number of parts in stock
     */

    public Part(int ID, String name, double price, int stock, int min, int max) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * This method is ID setter
     * @param ID part ID
     */

    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * This method is ID getter
     * @return part ID
     */

    public int getID() {
        return ID;
    }

    /**
     * This method is Name setter
     * @param name part Name
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method is Name getter
     * @return part Name
     */

    public String getName() {
        return name;
    }

    /**
     * This method is Price setter
     * @param price part price
     */

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * This method is Price getter
     * @return part Price
     */

    public double getPrice() {
        return price;
    }

    /**
     * This method is Stock getter
     * @return part inventory stock
     */

    public int getStock() {
        return stock;
    }

    /**
     * This method is Minimum setter
     * @param min minimum number of part
     */


    public void setMin(int min) {
        this.min = min;
    }

    /**
     * This method is Minimum getter
     * @return minimum number of part
     */

    public int getMin() {
        return min;
    }

    /**
     * This method is Maximum setter
     * @param max maximum number of part
     */


    public void setMax(int max) {
        this.max = max;
    }

    /**
     * This method is Maximum getter
     * @return max maximum number of part
     */

    public int getMax() {
        return max;
    }


}

