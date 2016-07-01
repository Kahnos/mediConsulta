/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicoapp;

import java.io.IOException;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * FXML Controller class
 *
 * @author Rusben Guzman
 */
public class crearCita extends VBox {
    
    // Declaracion de los componenetes de la ventana
    @FXML private TextField patient_tx;
    @FXML private TextArea motivo_txta;
    @FXML private Button btn_add_patient;
    @FXML private Button btn_aceptar;
    @FXML private Button btn_cancelar;
    @FXML private Pane mainPanel;
    @FXML private ComboBox<String> tipo_e_cb;
    @FXML private Label nombre_l;
    @FXML private Label apellido_l;
    private Day dayMedic;
    private boolean cancel;
    
    /*
     * crearCita: constructor del FXML_custom_control
     */
    public crearCita() {
   
        // Carga el archivo FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("custom_control_crearCita.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        tipo_e_cb.getItems().addAll("Consulta","Reunion","Vacaciones","Congreso","Personal");
        nombre_l.setText("");
        apellido_l.setText("");
    }

    /*
     * display: 
     */
    @FXML
    public Day display (TableView<Appointment> table, ArrayList<Patient> patients, Day dayM, String date) {
        // Verificar si el dia existe: si no existe crea el dia en la bd
        dayMedic = dayM;
        
       // Se declara una window y se inicializa  
       Stage window = new Stage(); 
       window.initModality(Modality.APPLICATION_MODAL);
       window.setTitle("mediConsulta - Crear evento");
       window.setMinWidth(250);
       
      // Se habilita los input segun la tipo de evento
      tipo_e_cb.setOnAction(e -> {
          System.out.println("Cambio de tipo");
          if (!tipo_e_cb.getValue().equals("Consulta")) {
                patient_tx.setDisable(true);
                btn_add_patient.setDisable(true);
                nombre_l.setText("");
                apellido_l.setText("");
                patient_tx.setText("");
            } else {
              btn_add_patient.setDisable(false);  
              patient_tx.setDisable(false);
            }
       });
       //Se crean los manejadores de eventos de los controles
       btn_aceptar.setOnAction((ActionEvent e) -> {
           // Se obtiene la lista de los appointment del dia
           ObservableList<Appointment> citaSelect, allProductos;
           allProductos = table.getItems();
           // Se obtiene el appointment seleccionado
           citaSelect = table.getSelectionModel().getSelectedItems();
           Appointment appointmentSelect = citaSelect.get(0);
           // Se crea un appointment auxiliar
           Appointment np = new Appointment(); 
           
           if (!tipo_e_cb.getValue().equals("Consulta")) {
                // Se le asignan los datos al appoinment
                // ID del paciente
                np.setPatientID("");
                // Start
                np.setStart(appointmentSelect.getStart());
                // End
                np.setEnd(appointmentSelect.getEnd());
                //  Slot
                np.setSlot(np.getStart() + " - " + np.getEnd());
                // Nombre del paciente
                np.setPatientName("");
                // Apellido del paciente
                np.setPatientLastName("");
                // Descripcion del paciente
                if (motivo_txta.getText().equals("")) {
                   np.setDescription(tipo_e_cb.getValue());
               } else
                    np.setDescription(motivo_txta.getText());
                // Tipo de evento 
                np.setEventType(tipo_e_cb.getValue());
            } else if (motivo_txta.getText().equals("") ||  // no tiene descripcion
                patient_tx.getText().equals("") ||          // no tiene cedula
                !Validations.isInt(patient_tx.getText()) || // La cedula no es un numero
                nombre_l.getText().equals("") ||            // no tiene nombre   
                apellido_l.getText().equals("")) {          // no tiene apellido
                    System.out.println("Tiene que llenar los campos para poder insertar en la tablaa");
                    return;
            } else {
                //-----------------------------------------------
                // Se obtitne el index el paciente seleccionado en el combobox para obtener sus datos
                // del arreglo de pacientes
                int patientIndex = 0, i;
                for (i = 0; i < patients.size() ; i++) {
                    if (patient_tx.getText().equals(patients.get(i).getPatientID())) {
                        patientIndex = i;
                        break;
                    }
                }
                // Se verifica si el paciente no esta registrado
                if (i == patients.size()) {
                    return;
                }
                // Se le asignan los datos al appoinment
                // ID del paciente
                np.setPatientID(patient_tx.getText());
                // Start
                np.setStart(appointmentSelect.getStart());
                // End
                np.setEnd(appointmentSelect.getEnd());
                //  Slot
                np.setSlot(np.getStart() + " - " + np.getEnd());
                // Nombre del paciente
                np.setPatientName(nombre_l.getText());
                // Apellido del paciente
                np.setPatientLastName(apellido_l.getText());
                // Descripcion del paciente
                np.setDescription(motivo_txta.getText());
                // Tipo de evento 
                np.setEventType(tipo_e_cb.getValue());
                // sexo
                np.setPatientSex(patients.get(i).getSex());
                // age
                DateTime dt1 = new DateTime(date);
                DateTime dt2 = new DateTime(patients.get(i).getBirthdate());
                DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy");
                int age = Integer.parseInt(fmt.print(dt1)) - Integer.parseInt(fmt.print(dt2));
                np.setPatientAge(Integer.toString(age));
                
            }
            // POST: agregar una cita al dia
            // Se modifica el slot de la tableCitas segun los datos del appointment auxiliar
            System.out.println("Start: " + np.getStart() + " end: " + np.getEnd() + "slot: " + np.getSlot());
            allProductos.set(table.getSelectionModel().getSelectedIndex(), np);
            // Se hace el Post
            this.dayMedic = HTTPRequest.addAppointment(dayMedic.getId(), np);
            System.out.println("En la creacion del evento: " + dayMedic.getDayAppointmentsPos(dayMedic.getDayAppointments().length-1).getId());
            System.out.println("Start: " + np.getStart() + " end: " + np.getEnd() + "slot: " + np.getSlot());
            // Se vacia el text area de motivo
            motivo_txta.clear();  
            window.close();
            });
       
        btn_cancelar.setOnAction((ActionEvent e) -> {
            this.cancel = true;
            window.close();
        });
       
        patient_tx.setOnAction(e -> {
            int i;
            for (i = 0; i < patients.size() ; i++) {
                if (patient_tx.getText().equals(patients.get(i).getPatientID())) {
                    nombre_l.setText(patients.get(i).getName());
                    apellido_l.setText(patients.get(i).getLastName());
                    break;
                }
            }
                                
            if (i == patients.size()) {
                nombre_l.setText("Paciente no \n registrado");
            }
        });
       
        patient_tx.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue) {
                    System.out.println("Textfield on focus");
                } else {
                    System.out.println("Textfield out focus");
                    int i;
                    for (i = 0; i < patients.size() ; i++) {
                        if (patient_tx.getText().equals(patients.get(i).getPatientID())) {
                            nombre_l.setText(patients.get(i).getName());
                            apellido_l.setText(patients.get(i).getLastName());
                            break;
                        }
                    }
                                
                    if (i == patients.size()) {
                        nombre_l.setText("Paciente no registrado");
                        apellido_l.setText("");
                    }
                }
            }
        });
       
       // Se crea un panel, se le asigna el panel a una scene y se le asigna la scene a la window
       VBox vb = new VBox();
       vb.getChildren().addAll(mainPanel);
       vb.setAlignment(Pos.CENTER);
       Scene scene = new Scene(vb);
       window.setScene(scene);
       window.setOnCloseRequest(e ->{
           System.out.println("Cancelado desde la x");
           this.cancel = true;
       });
       
       window.showAndWait();
       return dayMedic;
    
    }

    public boolean isCancel() {
        return cancel;
    }
}
