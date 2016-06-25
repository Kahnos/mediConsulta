package medicoapp;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.joda.time.DateTime;

/**
 * FXML Controller class
 *
 * @author Rusben Guzman
 */
public class Custom_control_agregarPacienteController extends VBox {

        // Elementos que se llenan en el formulario
        @FXML private TextField nombre_tf;
        @FXML private TextField apellido_tf;
        @FXML private TextField cedula_tf;
        @FXML private TextField email_tf;
        @FXML private TextField telefono_tf;
        @FXML private ComboBox<String> sex_cb;
        @FXML private TextField altura_tf;
        @FXML private TextField peso_tf;
        @FXML private DatePicker fn_dtpk;
        @FXML private TextField alergias_tx;
        @FXML private TextField ant_tx;
        
        // Botones de accion
        @FXML private Button btn_add;
        @FXML private Button btn_cancelar;
        @FXML private Button btn_add_alergia;
        @FXML private Button btn_add_ant;
        @FXML private Button btn_del_alergia;
        @FXML private Button btn_del_ant;
        
        // Listas
        @FXML private ListView<String> list_alergias;
        @FXML private ListView<String> list_ant;
        
        // Radio Buttons 
        @FXML private RadioButton rbtn_add;
        @FXML private RadioButton rbtn_del;
        @FXML private RadioButton rbtn_up;
        
        
        // Panel principal
        @FXML private AnchorPane mainPanel;
       
        private Patient[] patietnsAux;
    
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
       //-------------------------
        sex_cb.getItems().add("Masculino");
        sex_cb.getItems().add("Femenino");
   
}
        
/*
     * display: 
     */
    @FXML
    public void display (Patient[] patients)
    {
       patietnsAux = patients;
       // Se declara una window 
       Stage window = new Stage();
       window.initModality(Modality.APPLICATION_MODAL);
       window.setTitle("mediConsulta - Agregar paciente");
       window.setMinWidth(250);



        //Se crean los manejadores de eventos de los controles
        btn_add.setOnAction((ActionEvent e) -> {
                // Verifica si la altura y la y  
                if (!(Validations.isDouble(peso_tf.getText())) || !(Validations.isDouble(altura_tf.getText())) ){
                    System.out.println("El peso o la altura no es numero, revisa porfavor eso...");
                    return;
                }
                // Verifica que hallan valores en los campos obligatorios 
                // Campos obligatorios: nombre, apellido, cedula, altura, peso telefono o email
                if (nombre_tf.getText().equals("") || 
                    apellido_tf.getText().equals("") ||
                    cedula_tf.getText().equals("") ||
                    (telefono_tf.getText().equals("") && email_tf.getText().equals("")) ||
                     sex_cb.getValue().equals("") ||
                     fn_dtpk.getValue() == null    ) { 
                        System.out.println("Tiene que llenar los campos para poder insertar en la tabla");
                        return;
                }
                
                // Hacer el POST: agregar un paciente a la BD
                Patient patientAdd = new Patient();
                //Nombre
                patientAdd.setName(nombre_tf.getText());
                //Apellido
                patientAdd.setLastName(apellido_tf.getText());
                //cedula
                patientAdd.setPatientID(cedula_tf.getText());
                //email
                patientAdd.setEmail(email_tf.getText());
                //telefono
                patientAdd.setPhoneNumber(telefono_tf.getText());
                //sexo
                patientAdd.setSex(sex_cb.getValue());
                //altura
                patientAdd.setHeight(Double.parseDouble(peso_tf.getText()));
                //peso
                patientAdd.setWeight(Double.parseDouble(peso_tf.getText()));
                // Hace un parse de Localdate a Date y de Date a ISOdate
                LocalDate localDatePatient = fn_dtpk.getValue();
                Date datePatient = Date.from(fn_dtpk.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                DateTime dt = new DateTime(datePatient);
                patientAdd.setBirthdate(dt.toString());
                // Agregar alergias
                ObservableList<String> items_alergias = list_alergias.getItems();
                String[] alergiasAux = new String[items_alergias.size()];
                for (int i = 0; i < items_alergias.size() ; i++) {
                    alergiasAux[i] = items_alergias.get(i);
                }
                patientAdd.setAllergies(alergiasAux);
                //  Agregar antecedentes
                ObservableList<String> items_ant = list_ant.getItems();
                String[] antAux = new String[items_ant.size()];
                for (int i = 0; i < items_ant.size() ; i++) {
                    antAux[i] = items_ant.get(i);
                }
                patientAdd.setMedicalBackgrounds(antAux);
                //System.out.println("Appointmet: " + patientAdd.getLastName());
                HTTPRequest.addPatient(patientAdd);
            });
        
        // Agregar/Quitar alergias
        btn_add_alergia.setOnAction(e -> {
            if (alergias_tx.getText().equals("") || alergias_tx.getText() == null) {
            } else {
                list_alergias.getItems().add(alergias_tx.getText());
            } 
        });
        
        btn_del_alergia.setOnAction(e -> {
            ObservableList<String> allAlergias, alergiaSelect;
            allAlergias = list_alergias.getItems();
            alergiaSelect = list_alergias.getSelectionModel().getSelectedItems();
            
            if (!alergiaSelect.isEmpty()) {
                allAlergias.remove(alergiaSelect.get(0));
            }
            
        });
       
        // Agregar/Quitar antecedentes
        btn_add_ant.setOnAction(e -> {
            if (ant_tx.getText().equals("") || ant_tx.getText() == null) {
            } else {
                list_ant.getItems().add(ant_tx.getText());
            }
            
        });
        
        btn_del_ant.setOnAction(e -> {
            ObservableList<String> allAnt, antSelect;
            allAnt = list_ant.getItems();
            antSelect = list_ant.getSelectionModel().getSelectedItems();
            
            if (!antSelect.isEmpty()) {
                allAnt.remove(antSelect.get(0));
            }    
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
    
