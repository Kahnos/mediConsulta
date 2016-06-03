package medicoapp;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Rusben Guzman
 */
public class Custom_control_agregarPacienteController extends VBox {

        @FXML private TextField nombre_tf;
        @FXML private TextField apellido_tf;
        @FXML private TextField cedula_tf;
        @FXML private TextField email_tf;
        @FXML private TextField telefono_tf;
        
        @FXML private Button btn_add;
        @FXML private Button btn_cancelar;
        
        @FXML private AnchorPane mainPanel;
        
    
        public Custom_control_agregarPacienteController() {
        // Carga el archivo FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("custom_control_agregarPaciente.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
   
}
        
/*
     * display: 
     */
    @FXML
    public void display ()
    {
       
       // Se declara una window 
       Stage window = new Stage(); 
       window.initModality(Modality.APPLICATION_MODAL);
       window.setTitle("mediConsulta - Agregar paciente");
       window.setMinWidth(250);



        //Se crean los manejadores de eventos de los controles
        btn_add.setOnAction((ActionEvent e) -> {
                 if (nombre_tf.getText().equals("") || 
                    apellido_tf.getText().equals("") ||
                    cedula_tf.getText().equals("") ||
                    (telefono_tf.getText().equals("") && email_tf.getText().equals(""))) { 
                        System.out.println("Tiene que llenar los campos para poder insertar en la tablaa");
                        return;
                }
                
                // Hacer el POST 
                 
            });
       
       btn_cancelar.setOnAction((ActionEvent e) -> {
           window.close();
       });
       // Se crea un panel, se le asigna el panel a una scene y se le asigna la scene a la window
       VBox vb = new VBox();
       vb.getChildren().addAll(mainPanel);
       vb.setAlignment(Pos.CENTER);
       Scene scene = new Scene(vb);
       window.setScene(scene);
       window.showAndWait();
    
    }
        
    }
    
