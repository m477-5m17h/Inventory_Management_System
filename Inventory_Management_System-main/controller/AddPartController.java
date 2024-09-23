package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

/**
 * FXML Controller class provides the logic to for the add part screen corresponding with the view of the add part application
 *
 * @author Matthew Smith
 */
public class AddPartController implements Initializable {
    @FXML private RadioButton partInhouseRadio;
    @FXML private ToggleGroup addPartToggleGroup;
    @FXML private RadioButton partOutsourcedRadio;
    @FXML private Button addPartCancelButton;
    @FXML private Button addPartSaveButton;
    @FXML private Label addPartMachineIDCompanyLabel;
    @FXML private TextField addPartIDText;
    @FXML private TextField addPartNameText;
    @FXML private TextField addPartInvText;
    @FXML private TextField addPartPriceText;
    @FXML private TextField addPartMaxText;
    @FXML private TextField addPartMachineIDText;
    @FXML private TextField addPartMinText;
/**
 * Added an event for returning to the main screen instead of putting it in the class.
 * Upon cancelling the add part button being pressed the user will have to confirm and then be returned to the main screen.
 * @param event pressing the cancel button
 * @throws IOException 
 */
    @FXML
    public void addPartCancelButtonAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("!!WARNING!!");
        alert.setContentText("Are you sure you want to cancel and return to the main screen?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            backToMain(event);
        }
    }
    /**
     * This allows the user to add a part to the inventory system 
     * @param event pressing the save button
     * @throws IOException 
     */
    @FXML
    void addPartSaveButtonAction(ActionEvent event) throws IOException {
        try{
            int id = 0;
            String name = addPartNameText.getText();
            Double price = Double.parseDouble(addPartPriceText.getText());
            int stock = Integer.parseInt(addPartInvText.getText());
            int min = Integer.parseInt(addPartMinText.getText());
            int max = Integer.parseInt(addPartMaxText.getText());
            int machineId;
            String companyName;
            boolean addPartComplete = false;
            
            if(name.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("!!ERROR!!");
                alert.setContentText("The value entered is incorrect, the part must have a name.");
                alert.showAndWait();
            } else {
                if(partMinCorrect(min,max) && partInvCorrect(min, max, stock)){
                    if(partInhouseRadio.isSelected()){
                        try {
                            machineId = Integer.parseInt(addPartMachineIDText.getText());
                            InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                            newInHousePart.setId(Inventory.getNewPartId());
                            Inventory.addPart(newInHousePart);
                            addPartComplete = true;
                        } catch(Exception e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("!!ERROR!!");
                            alert.setContentText("The value entered is incorrect, the machine ID must be a numeric value.");
                            alert.showAndWait();
                        }
                    }
                    if(partOutsourcedRadio.isSelected()){
                        companyName = addPartMachineIDText.getText();
                        Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                        newOutsourcedPart.setId(Inventory.getNewPartId());
                        Inventory.addPart(newOutsourcedPart);
                        addPartComplete = true;
                    }
                    if(addPartComplete){
                        backToMain(event);
                    }
                }
            }
        } catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("!!ERROR!!");
            alert.setContentText("The value entered is incorrect, there can be no empty fields.");
            alert.showAndWait();
        }
    }
    /**
     * this sets the radio button to Inhouse when pressed allowing the value to be the machine id
     * @param event selecting inhouse on the radio button
     */
    @FXML void partInhouseRadioButton(ActionEvent event) {
        addPartMachineIDCompanyLabel.setText("Machine ID");
    }
    /**
     * This sets the radio button to outsourced when pressed changing to company name
     * @param event pressing radio button
     */
    @FXML void partOutsourcedRadioAction(ActionEvent event) {
        addPartMachineIDCompanyLabel.setText("Company Name");
    }
    /**
     * This is a check to make sure the inventory count in the part values is correct, not above maximum or below minimum
     * @param min
     * @param max
     * @param stock
     * @return 
     */
    
    private boolean partInvCorrect (int min, int max, int stock){
        boolean correctValues = true;
        if(stock < min || stock > max){
            correctValues = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("!!ERROR!!");
            alert.setContentText("The values entered are incorrect, the inventory value must be between the minimum and maximum inventory level values.");
            alert.showAndWait();
        }
        return correctValues;
    }
    /**
     * This is a check to determine that the minimum value lies between 0 and the maximum value not inclusive.
     * @param min
     * @param max
     * @return 
     */
    private boolean partMinCorrect(int min,int max) {
        boolean correctValues = true;
        if(min <= 0 || min >= max){
            correctValues = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("!!ERROR!!");
            alert.setContentText("The values entered are incorrect, the minimum has to be greater than 0 and less than the maximum quantity.");
            alert.showAndWait();
        }
        return correctValues;
    }
    /**
     * This is to return back to the main screen upon canceling and saving
     * @param event
     * @throws IOException 
     */
    private void backToMain(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * initializes the controller and allows the inhouse button to remain toggled on when opened
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partInhouseRadio.setSelected(true);
    }

}


