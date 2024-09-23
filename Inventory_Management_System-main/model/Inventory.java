package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the model for the inventory for the parts and products controllers.
 * @author Matt Smith
 */
public class Inventory {
    /**
     * The lists of both parts and products in inventory respectively
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
/**
 * This adds a part to the inventory
 * @param newPart 
 */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    /**
     * This adds a new product to the inventory.
     * @param newProduct 
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    /**
     * Creates product and part ids for each new entry
     */
    private static int partId = 0;
    private static int productId = 0;
    /**
     * Creates a new part ID
     * @return 
     */
    public static int getNewPartId(){
        return ++partId;
    }
    /**
     * Creates a new product ID
     * @return 
     */
    public static int getNewProductId(){
        return ++productId;
    }
    /**
     * This searches the list of parts via the Id of the part and returns it
     * @param partId
     * @return The part is returned if found, initialized with null, and will return with if not found
     */
    public static Part lookupPart(int partId){
        Part partFound = null;
        for(Part part : allParts) {
            if (part.getId() == partId) {
                partFound = part;
            }
        }
        return partFound;
    }
    
    /**
     * This searches the list of products via the ID of the product and returns it
     * @param productId
     * @return The product if found, null otherwise.
     */
    public static Product lookupProduct(int productId) {
        Product productFound = null;
        for(Product product : allProducts){
            if (product.getId() == productId){
                productFound = product;
            }                 
        }
        return productFound;
    }
    /**
     * This searches the list of parts via name of the part and returns it.
     * @param partName
     * @return the list of parts with that name
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        for(Part part: allParts){
            if(part.getName().contains(partName)){
                partsFound.add(part);
            }
        }
        return partsFound;
    }
    /**
     * This searches the list of products via name of the product and returns it
     * @param productName 
     * @return the list of products with that name
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productsFound = FXCollections.observableArrayList();
        for(Product product : allProducts){
            if(product.getName().contains(productName)){
                productsFound.add(product);
            }
        }
        return productsFound;
    }
    /**
     * Updates by replacing a part in the observable list of parts
     * @param index  of the part being replaced
     * @param selectedPart the part replacing the original part.
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }
    /**
     * Updates by replacing a product in the observable list.
     * @param index of the product being replaced
     * @param selectedProduct the product replacing the original product. 
     */
    public static void updateProduct(int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
    }
    /**
    *Removes a part from the list of parts in the observable list
    *@param selectedPart
    *@return Boolean removing parts.
    */
    public static boolean deletePart (Part selectedPart){
        if(allParts.contains(selectedPart)){
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }
    /**
    *Removes a product from the list of products in an observable list
    *@param selectedProduct
    *@return Boolean removing products.
    */
    public static boolean deleteProduct (Product selectedProduct){
        if(allProducts.contains(selectedProduct)){
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }
    /**
     * Retrieves a list of all the parts in the inventory in the parts observable list.
     * @return the list of part objects in the observable list allParts
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    /**
     * Retrieves a list of all the products in the inventory in the products observable list
     * @return the list of product objects in the observable list allProducts
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
}
