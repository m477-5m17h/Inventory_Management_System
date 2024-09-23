package model;

/**
 * In House inventory model package for parts
 * @author Matthew Smith
 */
public class InHouse extends Part {
    /**
     * When InHouse is selected, returns the machine ID
     */
    private int machineId;
    /**
     * Parameterized constructor for an inhouse instance 
     * @param id
     * @param name
     * @param price
     * @param stock is the same as level in inventory
     * @param min the lowest level allowed in stock
     * @param max the max level allowed in stock
     * @param machineId 
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
    /**
     * Getter for encapsulation of the MachineID
     * @return 
     */
    public int getMachineId() {
        return machineId;
    }
    /**
     * Setter for encapsulation of the machineId
     * @param machineId 
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
    
}
