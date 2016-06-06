/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicoapp;

import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Rusben Guzman
 */
public class crearCita extends VBox {
    
    // Declaracion de los componenetes de la ventana
    @FXML private ComboBox<String> patient_cb;
    @FXML private TextArea motivo_txta;
    @FXML private Button btn_add_patient;
    @FXML private Button btn_aceptar;
    @FXML private Button btn_cancelar;
    @FXML private Pane mainPanel;
    private Day dayMedic;
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
    }

    /*
     * display: 
     */
    @FXML
    public Day display (TableView<Appointment> table, Patient[] patients, Day dayM) {
        // Verificar si el dia existe: si no existe crea el dia en la bd
        dayMedic = dayM;
        
       // Se declara una window y se inicializa  
       Stage window = new Stage(); 
       window.initModality(Modality.APPLICATION_MODAL);
       window.setTitle("mediConsulta - Crear evento");
       window.setMinWidth(250);
       
       // Agregando los pacientes a la combobox
       for (int i = 0; i < patients.length ; i++){
           patient_cb.getItems().add(patients[i].getName());
       }

        //Se crean los manejadores de eventos de los controles
        btn_aceptar.setOnAction((ActionEvent e) -> {
                 if (motivo_txta.getText().equals("") || 
                     patient_cb.getValue().equals("") ) { 
                        System.out.println("Tiene que llenar los campos para poder insertar en la tablaa");
                        return;
                }

        //-----------------------------------------------
        // POST: agregar una cita al dia
                // Se obtitne el index el paciente seleccionado en el combobox para obtener sus datos
                // del arreglo de pacientes
                int patientIndex = patient_cb.getSelectionModel().getSelectedIndex();
                // Se obtiene la lista de los appointment del dia
                ObservableList<Appointment> citaSelect, allProductos;
                allProductos = table.getItems();
                // Se obtiene el appointment seleccionado
                citaSelect = table.getSelectionModel().getSelectedItems();
                Appointment appointmentSelect = citaSelect.get(0);
                // Se crea un appointment auxiliar
                Appointment np = new Appointment();
                // Se le asignan los datos al appoinment
                // ID del paciente
                np.setPatientID(patients[patientIndex].getPatientID());
                // Start
                np.setStart(appointmentSelect.getStart());
                // End
                np.setEnd(appointmentSelect.getEnd());
                //  Slot
                np.setSlot(appointmentSelect.getSlot());
                // Nombre del paciente
                np.setPatientName(patient_cb.getValue());
                // Apellido del paciente
                np.setPatientLastName(patients[patientIndex].getLastName());
                // Descripcion del paciente
                np.setDescription(motivo_txta.getText());
                // Tipo de evento 
                np.setEventType("Consulta");
                // Se modifica el slot de la tableCitas segun los datos del appointment auxiliar
                allProductos.set(table.getSelectionModel().getSelectedIndex(), np);
                // Se hace el Post
                this.dayMedic = HTTPRequest.addAppointment(dayMedic.getId(), np);
                System.out.println("En la creacion del evento: " + dayMedic.getDayAppointmentsPos(dayMedic.getDayAppointments().length-1).getId());
                // Se vacia el text area de motivo
                motivo_txta.clear();  
                window.close();
            });
       
       btn_cancelar.setOnAction((ActionEvent e) -> {
            //System.out.println(patient_cb.getSelectionModel().getSelectedIndex());
            window.close();
       });
       // Se crea un panel, se le asigna el panel a una scene y se le asigna la scene a la window
       VBox vb = new VBox();
       vb.getChildren().addAll(mainPanel);
       vb.setAlignment(Pos.CENTER);
       Scene scene = new Scene(vb);
       window.setScene(scene);
       window.showAndWait();
       return dayMedic;
    
    }
}
