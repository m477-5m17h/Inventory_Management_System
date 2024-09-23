package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import javafx.event.ActionEvent;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

/**
 * Main Screen controller logic for the application.
 *
 * @author Matt Smith
 */

public class MainScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private Button mainExitButton;
    @FXML private TableView<Part> mainScreenPartTable;
    @FXML private TableColumn<Part, Integer> partIDColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInventoryColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    @FXML private Button addPartButton;
    @FXML private Button deletePartButton;
    @FXML private Button modifyPartButton;
    @FXML private TextField partSearch;
    @FXML private TableView<Product> mainScreenProductTable;
    @FXML private TableColumn<Product, Integer> productIDColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Integer> productInventoryColumn;
    @FXML private TableColumn<Product, Double> productPriceColumn;
    @FXML private Button addProductButton;
    @FXML private Button deleteProductButton;
    @FXML private Button modifyProductButton;
    @FXML private TextField productSearch;
    
    
    /**
     * This changes the screen to the Add Part menu when pressed.
     * @param event
     * @throws IOException 
     */
    @FXML void addPartButtonAction(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/AddPart.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    /**
     * This changes the screen to the add product menu when pressed.
     * @param event
     * @throws IOException 
     */
    @FXML void addProductButtonAction(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/AddProduct.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    /**
     * This deletes the selected part by the user and alerts the user if there is no part selected and requires a confirmation to continue to permanently delete.
     * @param event 
     */
    @FXML void deletePartButtonAction(ActionEvent event) {
        Part selectedPart = mainScreenPartTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("!!ERROR!!");
            alert.setContentText("The input is invalid, there was no part selected.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("!!CONFIRM!!");
            alert.setContentText("Are you sure you want to delete the selected part?");
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            }
        }
    }
    /**
     * This allows the user to delete the products the user selects and puts an alert if a the user tries to delete a product with an existing part associated to it.
     * @param event 
     */
    @FXML void deleteProductButtonAction(ActionEvent event) {
        Product selectedProduct = mainScreenProductTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("!!ERROR!!");
            alert.setContentText("The input is invalid, there was no product selected.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("!!CONFIRM!!");
            alert.setContentText("Are you sure you want to delete the selected product?");
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.isPresent() && result.get() == ButtonType.OK){
                ObservableList<Part> associatedPartsList = selectedProduct.getAllAssociatedParts();
                
                if (associatedPartsList.size() >= 1){
                    alert.setTitle("!!ERROR!!");
                    alert.setContentText("The input is invalid, all associated parts to the product must be selected.");
                    alert.showAndWait();
                } else {
                    Inventory.deleteProduct(selectedProduct);
                }
            }
        }
    }
    /**
     * Exits the inventory program
     * @param event 
     */
    @FXML void mainExitButtonAction(ActionEvent event) {
        System.exit(0);
    }
    /**
     * Switches the controller to the modify part screen and will prevent the user from continuing if there is no part selected to modify
     * @param event
     * @throws IOException 
     */
    @FXML void modifyPartButtonAction(ActionEvent event) throws IOException {
        partToModify = mainScreenPartTable.getSelectionModel().getSelectedItem();

        if (partToModify == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("!!ERROR!!");
            alert.setContentText("The input is invalid, you cannot modify an unselected part!");
            alert.showAndWait();
        } else {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/ModifyPart.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
    /**
     * Switches the controller to the modify product screen and will prevent the user from continuing if there is no product selected to modify
     * @param event
     * @throws IOException 
     */
    @FXML void modifyProductButtonAction(ActionEvent event) throws IOException {
        productToModify = mainScreenProductTable.getSelectionModel().getSelectedItem();

        if (productToModify == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("!!ERROR!!");
            alert.setContentText("The input is invalid, you cannot modify an unselected product!");
            alert.showAndWait();
        } else {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/ModifyProduct.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
    /**
     * This searches in the parts text field and returns the results of the parts via name or ID
     * @param event 
     */
    @FXML void partSearchAction(ActionEvent event) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchPartList = partSearch.getText();
        for(Part part : allParts) {
            if(String.valueOf(part.getId()).contains(searchPartList) || part.getName().contains(searchPartList)){
            partsFound.add(part);
            }
           }
           mainScreenPartTable.setItems(partsFound);
           if(partsFound.isEmpty()) {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("!!ERROR!!");
               alert.setContentText("The part was not found!!");
               alert.showAndWait();
           } 
    }
    /**
     * This searches in the products text field and returns the results of the products table, via name or ID
     * @param event 
     */
    @FXML void productSearchAction(ActionEvent event) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> productsFound = FXCollections.observableArrayList();
        String searchProductList = productSearch.getText();
        for(Product product : allProducts){
            if(String.valueOf(product.getId()).contains(searchProductList) || product.getName().contains(searchProductList)){
                productsFound.add(product);
            }
        }
        mainScreenProductTable.setItems(productsFound);
        if(productsFound.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("!!ERROR!!");
            alert.setContentText("The product was not found!!");
            alert.showAndWait();
        }
    }
    
    
    private static Part partToModify;
    public static Part getPartToModify(){
        return partToModify;
    }
    private static Product productToModify;
    public static Product getProductToModify(){
        return productToModify;
    }
    
    /**
     * Initializes the parameters for the main screen and fills in the table with the values.
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainScreenPartTable.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        mainScreenProductTable.setItems(Inventory.getAllProducts());
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }    
    
}