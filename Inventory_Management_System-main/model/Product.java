package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Model for the products that contain parts associated with it
 * @author Matthew Smith
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    /**
     * parameterized constructor for the new product object
     * @param id product id 
     * @param name product name
     * @param price product price
     * @param stock product inventory level
     * @param min product minimum value
     * @param max product maximum value
     */
    public Product (int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
    /**
     * Adds the part to the list associated with the product
     * @param part 
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
    /**
     * Deletes the part from the list associated with the product
     * @param selectedAssociatedPart the associated part that needs to be removed
     * @return 
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if (associatedParts.contains(selectedAssociatedPart)){
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else
            return false;
    }
}
