package medicoapp;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
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
        final ToggleGroup group = new ToggleGroup();    
        
        //Variables auxiliares
        Patient p;
        
        // Panel principal
        @FXML private AnchorPane mainPanel;
       
        private ArrayList<Patient> patietnsAux;
    
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
        
       // ----- grupos de los radio buttons

       rbtn_add.setToggleGroup(group);
       rbtn_up.setToggleGroup(group); 
}
        
/*
     * display: 
     */
    @FXML
    public void display (ArrayList<Patient> patients)
    {
        
        patietnsAux = patients;
        // Se declara una window 
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("mediConsulta - Modulo Pacientes");
        window.setMinWidth(250);
       
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
               RadioButton chk = (RadioButton)t1.getToggleGroup().getSelectedToggle(); 
               cedula_tf.setText("");
               vaciarInputs();
               System.out.println("Selected Radio Button - " + chk.getText());
               // Eventos cuando se selecciona agregar
               if (chk.getText().equals("Agregar")) {
                   btn_add.setText("Agregar");   
                   enableAdd();
                   actionAgregar(patients);
                   actionAddDeleteLists();
               // Eventos cuando se selecciona modificar    
               } else if (chk.getText().equals("Modificar")) {
                    btn_add.setText("Modificar");
                    disableDelete();
                    actionUpdate(patients);
               // Eventos cuando se selecciona eliminar   
               } if (chk.getText().equals("Eliminar")) {
                   btn_add.setText("Eliminar"); 
                   actionEliminar();
               }
               
            }
        });
        
        rbtn_add.setSelected(true);    
  
        // Evento para cerrar la ventana con el boton cancelar      
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
    
    
    // ----------------------------- FUNCIONES PARA ELIMINAR UN PACIENTE ---------------------------------
    public void disableDelete() {
        //inputs
        nombre_tf.setDisable(true);
        apellido_tf.setDisable(true);
        email_tf.setDisable(true);
        telefono_tf.setDisable(true);
        sex_cb.setDisable(true);
        altura_tf.setDisable(true);
        peso_tf.setDisable(true);
        fn_dtpk.setDisable(true);
        alergias_tx.setDisable(true);
        ant_tx.setDisable(true);
        //botones
        btn_add_alergia.setDisable(true);
        btn_add_ant.setDisable(true);
        btn_del_alergia.setDisable(true);
        btn_del_ant.setDisable(true);
    }
    
    public void actionEliminar() {
        disableDelete();
        focusFindPatient(1);
        btn_add.setOnAction(e -> {
        // Eliminar un paciente de la BD
            
        });
    }
    
    public void focusFindPatient(int mode) {
        
        cedula_tf.setOnAction(e -> {
        // se busca el pàciente
                    vaciarInputs();
                    int i;
                    for (i = 0; i < patietnsAux.size() ; i++) {
                        if (cedula_tf.getText().equals(patietnsAux.get(i).getPatientID())) {
                            p = patietnsAux.get(i);
                            break;
                        }
                    }
                    
                    if ( i != patietnsAux.size()) {
                        fillInputs(p);
                        if (mode == 2) enableAdd();
                    } else {
                        disableDelete();
                    }          
        });
        cedula_tf.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue) {
                    System.out.println("Cedula_tf on focus");
                } else
                {
                    System.out.println("Cedula_tf out focus");
                    // se busca el pàciente
                    vaciarInputs();
                    int i;
                    for (i = 0; i < patietnsAux.size() ; i++) {
                        if (cedula_tf.getText().equals(patietnsAux.get(i).getPatientID())) {
                            p = patietnsAux.get(i);
                            break;
                        }
                    }
                    
                    if ( i != patietnsAux.size()) {
                        fillInputs(p);
                        if (mode == 2) enableAdd();
                    } else {
                        disableDelete();
                    }                
                }
            }
            });
    
        }
    
    public void vaciarInputs() {
        //inputs
        nombre_tf.setText("");
        apellido_tf.setText("");
        email_tf.setText("");
        telefono_tf.setText("");
        sex_cb.setValue("");
        altura_tf.setText("");
        peso_tf.setText("");
        // Asignacion de la fecha
        fn_dtpk.setValue(null);
        // Asignacion de alergias
        ObservableList<String> list1 = FXCollections.observableArrayList();
        ObservableList<String> list2 = FXCollections.observableArrayList();
        list_alergias.setItems(list1);
        list_ant.setItems(list2);
    }
    
    public void fillInputs(Patient p) {
                //inputs
                nombre_tf.setText(p.getName());
                apellido_tf.setText(p.getLastName());
                email_tf.setText(p.getEmail());
                telefono_tf.setText(p.getPhoneNumber());
                sex_cb.setValue(p.getSex());
                altura_tf.setText(Double.toString(p.getHeight()));
                peso_tf.setText(Double.toString(p.getWeight()));
                // Asignacion de la fecha
                DateTime dt = new DateTime(p.getBirthdate());
                Date date = dt.toDate();
                LocalDate ldt = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                fn_dtpk.setValue(ldt);
                // Asignacion de alergias
                for (int i = 0; i < p.getAllergies().length ; i++) {
                    list_alergias.getItems().add(p.getAllergies()[i]);
                }
                // Asignacion de los antecedentes
                for (int i = 0; i < p.getMedicalBackgrounds().length ; i++) {
                    list_ant.getItems().add(p.getMedicalBackgrounds()[i]);
                }
            }
    
    //------------------------- FUNCIONES PARA AGREGAR UN PACIENTE -------------------------------------
    public void enableAdd() {
        //inputs
        nombre_tf.setDisable(false);
        apellido_tf.setDisable(false);
        //cedula_tf.setDisable(true);
        email_tf.setDisable(false);
        telefono_tf.setDisable(false);
        sex_cb.setDisable(false);
        altura_tf.setDisable(false);
        peso_tf.setDisable(false);
        fn_dtpk.setDisable(false);
        alergias_tx.setDisable(false);
        ant_tx.setDisable(false);
        //botones
        btn_add_alergia.setDisable(false);
        btn_add_ant.setDisable(false);
        btn_del_alergia.setDisable(false);
        btn_del_ant.setDisable(false);
        
        // Listas
        list_alergias.setDisable(false);
        list_ant.setDisable(false);
    }
    
    public void actionAgregar(ArrayList<Patient> patients){
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
                patientAdd.setHeight(Double.parseDouble(altura_tf.getText()));
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
                patients.add(HTTPRequest.addPatient(patientAdd));
            });
    }
    
    public void actionAddDeleteLists() {
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
    }
    //-------------------  FUNCIONES PARA MODIFICAR UN PACIENTE ----------------------------
    // Manejadores de eventos para
    public void actionUpdate(ArrayList<Patient> patients) {
        disableDelete();
        focusFindPatient(2);
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
                patientAdd.setHeight(Double.parseDouble(altura_tf.getText()));
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
                for (int i = 0; i < patients.size() ; i++) {
                    if (patients.get(i).getPatientID().equals(patientAdd.getPatientID())) {
                        patients.set(i,HTTPRequest.updatePatient(patients.get(i).getId(),patientAdd));
                        break;
                    }
                }           
            });
        }  
    }
    
