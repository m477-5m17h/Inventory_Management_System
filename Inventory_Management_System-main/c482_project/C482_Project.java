package c482_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

/**
 *This is a management system for inventory that runs an application with parts and products
 * @author Matt Smith
 */
public class C482_Project extends Application {

    /**
     * This initializes the program and creates the stage for the program
     * @param stage
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Inventory Management System");
        stage.show();
    }
    
    /**
     * This is the main method
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int partId = Inventory.getNewPartId();
        
        InHouse bikeTire = new InHouse(partId, "Bike Tire",  25.99, 6, 2, 100, 2);
        partId = Inventory.getNewPartId();
        InHouse bikeHandleBar = new InHouse(partId, "Bike Handle Bar", 15.00, 12, 1, 50, 1);
        partId = Inventory.getNewPartId();
        InHouse bikeSeat = new InHouse(partId, "Bike Seat", 10.50, 26, 1, 100, 3);
        partId = Inventory.getNewPartId();
        InHouse bikeChain = new InHouse(partId, "Bike Chain", 5.33, 31, 6, 30, 4);
        
        partId = Inventory.getNewPartId();
        Outsourced bikeFrame = new Outsourced(partId, "Bike Frame", 260.59, 10, 5, 25, "Trek");
        partId = Inventory.getNewPartId();
        Outsourced bikeHandleGrip = new Outsourced(partId, "Bike Handlebar Grips", 56.69, 6, 2, 600, "Ergon");
        
        Inventory.addPart(bikeTire);
        Inventory.addPart(bikeHandleBar);
        Inventory.addPart(bikeSeat);
        Inventory.addPart(bikeChain);
        Inventory.addPart(bikeFrame);
        Inventory.addPart(bikeHandleGrip);
        
        int productId = Inventory.getNewProductId();
        Product bike = new Product(productId, "Mountain Bike", 1200.53, 6, 3, 50);
        bike.addAssociatedPart(bikeTire);
        bike.addAssociatedPart(bikeSeat);
        bike.addAssociatedPart(bikeHandleBar);
        bike.addAssociatedPart(bikeChain);
        bike.addAssociatedPart(bikeFrame);
        bike.addAssociatedPart(bikeHandleGrip);
        Inventory.addProduct(bike);
        
        launch(args); 
    }
}
