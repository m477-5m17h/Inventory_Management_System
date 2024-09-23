package model;

/**
 * Model for a part when outsourced is selected in inventory management
 * @author Matthew Smith
 */
public class Outsourced extends Part {
    /**
     * The corresponding company name for the part
     */
    private String companyName;
    /**
     * Parameterized constructor of the outsourced instance
     * @param id
     * @param name
     * @param price
     * @param stock level of the inventory on hand
     * @param min minimum level allowed
     * @param max maximum level allowed
     * @param companyName name of the company providing the part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    /**
     * Getter for the company name 
     * @return 
     */
    public String getCompanyName() {
        return companyName;
    }
    /**
     * setter for the company name 
     * @param companyName 
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
}
