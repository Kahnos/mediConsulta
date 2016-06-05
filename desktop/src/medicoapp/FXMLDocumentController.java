/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicoapp;

import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.joda.time.DateTime;
/**
 *
 * @author Rusben Guzman
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Button btnCrearEvento;
    @FXML
    private Button btnEliminarEvento;
    @FXML
    private Button btnVerificarCitas;
    @FXML
    private Button btnRegistrarUsusarios;
    @FXML
    private TableView<Appointment> tableCitas;
    @FXML
    TableColumn<Appointment, String> nombreColumn;
    @FXML
    TableColumn<Appointment, String> horaColumn;
    @FXML
    TableColumn<Appointment, String> motivoColumn;
    @FXML
    TableColumn<Appointment, String> apellidoColumn;
    // @FXML
    private DatePicker date;
    @FXML
    private VBox Vmenu;
    @FXML
    private AnchorPane pacientes_pane; 
    @FXML
    private Label nombre_p_label;
    @FXML
    private Label apellido_p_label;
    @FXML
    private Label cedula_p_label;
    @FXML
    private Label email_p_label;
    @FXML
    private Label telefono_p_label;
    
    Day[] dayMedic;
    Patient[] patients;
    
    @FXML
    private void crearEvento(ActionEvent event) {
        System.out.println("Se abre la ventana para agregar un elemento a la lista");
        crearCita cr = new crearCita();
        cr.display(tableCitas);
    }
    
    
    @FXML
    private void eliminarEvento(ActionEvent event) throws IOException{
        System.out.println("Se abre la ventana para eliminar un elemento de la lista");
            ObservableList<Appointment> productosSelect, allProductos;
            allProductos = tableCitas.getItems();
            productosSelect = tableCitas.getSelectionModel().getSelectedItems();
                // POST: eleminar una cita del dia
                Appointment np = new Appointment();
                np.setStart(productosSelect.get(0).getStart());
                np.setSlot(productosSelect.get(0).getSlot());
                np.setPatientName("");
                np.setDescription("");
                allProductos.set(tableCitas.getSelectionModel().getSelectedIndex(), np);
}
    
    @FXML
    private void agregarPaciente(ActionEvent event) {
        System.out.println("Se abre la ventana para agregar un paciente a la BD");
        Custom_control_agregarPacienteController ap = new Custom_control_agregarPacienteController();
        ap.display();
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Obtenemos los deias del medico y los mete en el arreglo dayMedic
        dayMedic = HTTPRequest.getDays("22824486");
        // Se obtienen todos los pacientes de la bd
        patients = HTTPRequest.getAllPatients();
        System.out.println(patients[0].getEmail());
        // Se inicializa el calendario y el popup
        DatePicker dp = new DatePicker(LocalDate.now());
        DatePickerSkin datePickerSkin = new DatePickerSkin(dp);
        this.date = dp;
        // Se obtine el poopup del calendario
        Node popupContent = datePickerSkin.getPopupContent();
        Vmenu.getChildren().addAll(popupContent);
        //System.out.println("fecha :  "+dp.getValue());

        // Inicialización de las filas por bloque de horas en la tabla
        tableCitas.setEditable(true);
        Date dte = new Date(2016,06,02,00,00,00);
        Calendar c = Calendar.getInstance();
        c.setTime(dte);
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        
        for (int i = 0;i < 24; i++) {
            Appointment a = new Appointment();
            a.setStart(dateFormat.format(c.getTime()));
            a.setSlot(dateFormat.format(c.getTime()) + " - ");
            c.add(Calendar.HOUR_OF_DAY, 1);
            a.setEnd(dateFormat.format(c.getTime()));
            a.setSlot(a.getSlot()+dateFormat.format(c.getTime()));
            tableCitas.getItems().add(a);
        }
        // Inicializacion de las columnas de la tabla
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        horaColumn.setCellValueFactory(new PropertyValueFactory<>("slot"));
        motivoColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        // Manejador de los eventos de el calendario
        
        //GET: obtener todos los dias y ponerlos en un arreglo de appointment
        dp.setOnAction(e -> {
               int i,j;
               // Se hace el parce de ISOdate a 
               System.out.println(dayMedic[0].getDate());
               DateTime dt = new DateTime(dayMedic[0].getDate());
               // Formato de comparacion de las fehcas
               DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
               // Se obtiene la fecha que se selecciono en el calendario
               String selectedDateString = dtf.format(Date.from(this.date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
               
               System.out.println("Fecha seleccionada del calendario: " + selectedDateString);               
               
                //Busco el dia en el arreglo segun el dia seleccionado en el calendario 
               for (i=0; (i < dayMedic.length) ;i++) {
                   System.out.println(dtf.format(dt.toDate()));
                   //Si encuentra el dia en el arreglo hace break y se obtiene la posicion del dia con "i"
                   if ( dtf.format(dt.toDate()).equals(selectedDateString) ){
                       System.out.println("Esta fecha es igual: " + dtf.format(dt.toDate()));
                       break;
                   }
                   // Se pasa al siguiente dia
                   dt = new DateTime(dayMedic[i].getDate());
               }
               // Si no encuentre el dia en el arreglo retorna
               if ( i == dayMedic.length ) {
                   System.out.println("Este dia no tiene citas");
                   return;}
               // Se recorren los appointments y se insertan en su slot correspondiente de la tabla
               for (j=0;j<dayMedic[i].getDayAppointments().length;j++){
                   Appointment aux = dayMedic[i].getDayAppointmentsPos(j);
                   // Se la asigna el slot al dia
                   String strStart = aux.getStart();
                   
                   aux.setSlot(aux.getStart() + " - " + aux.getEnd());
                  
                    // Se imprimen los datos para verificar que son correctos
                   System.out.println("Appointment " + j + ":");
                   System.out.println("Start: " + aux.getStart());
                   System.out.println("End: " + aux.getEnd());
                   System.out.println("slot: " + aux.getSlot());
                   System.out.println("Type: " + aux.getEventType());
                   
                   // Se verifica si es el slot correspondiente y se inserta ç
                   String strSlot;
                   for (int k = 0;k < tableCitas.getItems().size();k++) {
                       strSlot = tableCitas.getItems().get(k).getSlot();
                       if (aux.getSlot().equals(strSlot)) {
                           // Si es una hora de descanso o el doctor no va a estar
                           if (!aux.getEventType().equals("Consulta")){
                               aux.setDescription(aux.getEventType());
                           }
                           // Se crea un nuevo appointemenet y se inserta en la posicion del slot
                           Appointment appSlot = new Appointment(aux.getStart(),aux.getEnd(),
                           aux.getEventType(),aux.getPatientID(),aux.getPatientName(),aux.getDescription()
                           ,aux.getPatientLastName(),aux.getStart(),aux.getEnd());
                           appSlot.setSlot(aux.getSlot());
                           tableCitas.getItems().set(k, appSlot);
                           break;
                       }
                   } 
               }
               
        });
        //------------------- Manejador del evento cuando se oprime una fila de la tableCitas ------------
       tableCitas.setRowFactory( tv -> {
            TableRow<Appointment> row = new TableRow<>();
            
           /*System.out.println(row.getIndex());
           // Appointment a = ((Appointment) row.getItem()).clone();
            //a.getSlot();
//            if (row.getItem().getDescription().equals("Descanso")) {
                                    row.setStyle("    -fx-background-color: green;\n" +
                                "    -fx-background-insets: 0, 1, 2;\n" +
                                "    -fx-background: -fx-accent;\n");
//            }
            
            */
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    Appointment rowData = row.getItem();
                    
                    if (rowData.getPatientName().equals("")) {
                        nombre_p_label.setText("");
                        apellido_p_label.setText("");
                        cedula_p_label.setText("");
                        email_p_label.setText("");
                        telefono_p_label.setText("");
                        return;
                    } 
                    
                    // Se busca el paciente de la fila seleccionada
                    System.out.println();
                    int i;
                    for (i = 0; i < patients.length ; i++ ) {
                        System.out.println("patient: " + patients[i].getId() + " row: " + rowData.getPatientID());
                        if (patients[i].getId().equals(rowData.getPatientID()))
                            break;
                    }
                    
                    nombre_p_label.setText(patients[i].getName());
                    apellido_p_label.setText(patients[i].getLastName());
                    cedula_p_label.setText(patients[i].getPatientID());
                    email_p_label.setText(patients[i].getEmail());
                    telefono_p_label.setText(patients[i].getPhoneNumber());
                    
                    System.out.println(rowData + "\n" + rowData.getPatientName() + "\n" + rowData.getDescription() + " " + row.getIndex());
                }
            });
            return row ;
        });

    }
    
}
