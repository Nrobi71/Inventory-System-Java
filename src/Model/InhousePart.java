package Model;

/**
 * @author Nyssa Robinson
 * In House part Model contains part information that is In House
 * This class defines part information that is In House
 */

public class InhousePart extends Part {

    /**
     * This is a constructor
     * @param ID part ID
     * @param name part name
     * @param price part price
     * @param stock inventory stock
     * @param min minimum number of parts in stock
     * @param max maximum number of parts in stock
     * @param machineID part Machine ID
     */

    public InhousePart(int ID, String name, double price, int stock, int min, int max, int machineID) {
        super(ID, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /** This method is the Machine ID getter
     *
     * @return part Machine ID
     */

    public int getMachineID() {
        return machineID;
    }

    /**
     * This method is the part Machine ID associated with itself
     */

    private final int machineID;

}
