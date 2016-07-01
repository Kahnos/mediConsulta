/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicoapp;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.joda.time.DateTime;

/**
 * FXML Controller class
 *
 * @author Libny
 */
public class Custom_control_agregarUsuarioController extends VBox {
    
    @FXML private Button registerButton;
    
    //Fields
    @FXML private TextField ciField;
    @FXML private TextField passwordField;
    @FXML private TextField nameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField numberField;
    @FXML private ComboBox<String> sexField;
    @FXML private DatePicker birthdateField;
    
    
    //RadioButtons
    @FXML private RadioButton doctorRadio;
    @FXML private RadioButton asistentRadio;
    final ToggleGroup group = new ToggleGroup();
    
    private User user = new User("","","","","","","","","");
    
    
    // Panel principal
    @FXML private VBox mainPanel;

    public Custom_control_agregarUsuarioController() {
        // Carga el archivo FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("custom_control_agregarUsuario.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
          }
        sexField.getItems().addAll("Femenino","Masculino");
        doctorRadio.setToggleGroup(group);
        asistentRadio.setToggleGroup(group);
        doctorRadio.setSelected(true);
        sexField.setPromptText("Femenino");
        
        registerButton.setOnAction(e -> registerButtonClicked());
        
    }
    
    @FXML
    public void display(){
        // Se declara una window 
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("mediConsulta - Iniciar Sesión");
        window.setMinWidth(250);
        

        // Se crea un panel, se le asigna el panel a una scene y se le asigna la scene a la window
        VBox vb = new VBox();
        vb.getChildren().addAll(mainPanel);
        vb.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vb);
        window.setScene(scene);
        window.showAndWait();
        
    }    
    
    public String localdate2ISO(LocalDate local) {
        Date d = Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant());
        DateTime dt = new DateTime(d);
        return dt.toString();
    }
    
    private void registerButtonClicked(){
        if ((ciField.getText().equals("")) || (passwordField.getText().equals(""))
            || (nameField.getText().equals("")) || (lastNameField.getText().equals(""))
            || (emailField.getText().equals("")) || (emailField.getText().equals(""))
            || (numberField.getText().equals("")) || (birthdateField.getValue() == null)
           /* || (Validations.isInt(numberField.getText()) == false) || (Validations.isInt(ciField.getText()) == false)*/){
            System.out.println("Error: valor incorrecto.");
        }
        else{
            user.setUserID(ciField.getText());
            user.setPassword(passwordField.getText());
            user.setName(nameField.getText());
            user.setLastName(lastNameField.getText());
            user.setEmail(emailField.getText());
            user.setBirthdate(localdate2ISO(birthdateField.getValue()));
            user.setPhoneNumber(numberField.getText());      
            user.setSex(sexField.getValue());
            if (doctorRadio.isSelected()){
                user.setUserType("Medico");
            }
            else {
                user.setUserType("Ayudante");
            }

            System.out.println(user.toString());

            HTTPRequest.addUser(user);
            }
        
    }
    
}
