package Model;

/**
 * @author Nyssa Robinson
 * Outsourced part Model contains part information that is Outsourced
 * This class defines part information that is Outsourced
 */

public class OutsourcedPart extends Part {

    /**
     *
     * @param ID part ID
     * @param name part name
     * @param price part price
     * @param stock price stock
     * @param min minimum number of parts in stock
     * @param max minimum number of parts in stock
     * @param companyName part Company Name
     */

    public OutsourcedPart(int ID, String name, double price, int stock, int min, int max, String companyName) {
        super(ID, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** This method is the Company Name setter
     *
     * @param companyName Company Name
     */

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** This method is the Company Name getter
     *
     * @return part Company Name
     */

    public String getCompanyName() {
        return companyName;
    }

    /**
     * This method is the part Company Name
     */

    private String companyName;
}
