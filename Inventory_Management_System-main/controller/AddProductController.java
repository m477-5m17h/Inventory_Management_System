package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
 * FXML Controller class
 *
 * @author bc3mf
 */
public class AddProductController implements Initializable {
    @FXML private TextField addProductSearchText;
    @FXML private TextField addProductID;
    @FXML private TextField addProductName;
    @FXML private TextField addProductMin;
    @FXML private TextField addProductInv;
    @FXML private TextField addProductPrice;
    @FXML private TextField addProductMax;
    @FXML private TableView<Part> addProductTable;
    @FXML private TableColumn<Part, Integer> addProductPartIDCol;
    @FXML private TableColumn<Part, String> addProductPartNameCol;
    @FXML private TableColumn<Part, Integer> addProductInvCol;
    @FXML private TableColumn<Part, Double> addProductPartPriceCol;
    @FXML private TableView<Part> associatedProductTable;
    @FXML private TableColumn<Part, Integer> associatedPartIDCol;
    @FXML private TableColumn<Part, String> associatedProductPartNameCol;
    @FXML private TableColumn<Part, Integer> associatedProductInvCol;
    @FXML private TableColumn<Part, Double> associatedProductPriceCol;

 /* @FXML private Button addProductAddButton;

    @FXML private Button saveProductButton;

    @FXML private Button cancelProductButton;

    @FXML private Button removeAssociatedPartButton; */
    
    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();
    /**
     * This adds the part selected in the parts table to the associated parts table.
     * @param event 
     */
    @FXML void addProductAddAction(ActionEvent event) {
        Part selectedPart = addProductTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("!!Error!!");
            alert.setContentText("The part is not selected.");
            alert.showAndWait();
        } else {
            associatedPartsList.add(selectedPart);
            associatedProductTable.setItems(associatedPartsList);
        }
    }
    /**
     * Searches the value requested in the text field and then populates the table with the results
     * @param event 
     */
    @FXML void addProductSearchAction(ActionEvent event) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchPartList = addProductSearchText.getText();
        
        for(Part part : allParts){
            if(String.valueOf(part.getId()).contains(searchPartList)|| part.getName().contains(searchPartList)){
                partsFound.add(part);
        }
    }
        addProductTable.setItems(partsFound);
        
        if(partsFound.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("!!ERROR!!");
            alert.setContentText("The part does not exist.");
            alert.showAndWait();
        }
    }
    /**
     * Confirms with the user if they want to cancel their actions and returns them to the main screen
     * @param event
     * @throws IOException 
     */
    @FXML void cancelProductButtonAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("!!CONFIRM!!");
        alert.setContentText("Are you sure you want to cancel and return the main menu?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK){
            backToMain(event);
        }
    }

    @FXML void removeAssociatedPartButtonAction(ActionEvent event) {
        Part selectedPart = associatedProductTable.getSelectionModel().getSelectedItem();
        if(selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("!!ERROR!!");
            alert.setContentText("A part msy be selected from the list!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("!!CONFIRM!!");
            alert.setContentText("Are you sure you want to remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedPartsList.remove(selectedPart);
                associatedProductTable.setItems(associatedPartsList);
            }
            
        }
    }

    @FXML void saveProductButtonAction(ActionEvent event) throws IOException {
        try{
            int id = 0;
            String name = addProductName.getText();
            Double price = Double.parseDouble(addProductPrice.getText());
            int stock = Integer.parseInt(addProductInv.getText());
            int min = Integer.parseInt(addProductMin.getText());
            int max = Integer.parseInt(addProductMax.getText());
            
            if(name.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR, "The name field must contain a valid name!");
                alert.showAndWait();
            }    
            if(min > stock || stock < max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "The inventory must be greater than min and less than max!");
                    alert.showAndWait();
                }
            else if(min >= max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "The min cannot be greater than the max!");
                    alert.showAndWait();
                }
        
            Product product = new Product(id, name, price, stock, min, max);
            for(Part part : associatedPartsList){
                if(part != associatedPartsList) product.addAssociatedPart(part);
            }
            
            Inventory.getAllProducts().add(product);
    
            backToMain(event);
    } catch(NumberFormatException e) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "!!ERROR!!");
        alert.setContentText("The value is incorrect!!");
        alert.showAndWait();
    }
    }
    
    private void backToMain(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addProductPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        addProductTable.setItems(Inventory.getAllParts());
        
        associatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedProductInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
       // associatedProductTable.setItems(associatedPartsList);
    }    
    
}
