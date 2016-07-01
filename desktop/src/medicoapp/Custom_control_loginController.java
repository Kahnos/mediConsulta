/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicoapp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Libny
 */
public class Custom_control_loginController extends VBox {
    
    @FXML private Button loginButton;
    @FXML private TextField ciField;
    @FXML private PasswordField passwordField;
    @FXML private AnchorPane loginPane;
    @FXML private RadioButton doctorRadio;
    @FXML private RadioButton asistentRadio;
    @FXML private Label registerLabel;
    final ToggleGroup group = new ToggleGroup();
    
    private User user = new User("","","","","","","","","");
    
    // Panel principal
    @FXML private VBox mainPanel;
    
public Custom_control_loginController() {
    // Carga el archivo FXML
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("custom_control_login.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);
    try {
        fxmlLoader.load();            
    } catch (IOException exception) {
        throw new RuntimeException(exception);
      }
    doctorRadio.setToggleGroup(group);
    asistentRadio.setToggleGroup(group);
    doctorRadio.setSelected(true);
    
    loginButton.setOnAction(e -> loginButtonClicked());
    
    loginPane.setBackground(new Background(new BackgroundFill(Color.web("5C6BC0"),
            CornerRadii.EMPTY, Insets.EMPTY)));
    
    registerLabel.setOnMouseClicked(e-> registerLabelClicked());
}

@FXML
public User display(){
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
    return user;
    
}

private void loginButtonClicked(){
    if((ciField.getText().equals("")) || (passwordField.getText().equals(""))
        || (Validations.isInt(ciField.getText()) == false)){
        System.out.println("Error: valor incorrecto.");
    }
    else {
        user.setId(ciField.getText());
        user.setPassword(passwordField.getText());
        if (doctorRadio.isSelected()){
            user.setUserType("Medico");
        }else if (asistentRadio.isSelected()){
            user.setUserType("Ayudante");
        }        
    }
    
}

private void registerLabelClicked(){
    Custom_control_agregarUsuarioController addUser = new Custom_control_agregarUsuarioController();
    addUser.display();
}
    
}
