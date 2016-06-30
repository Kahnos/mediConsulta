package medicoapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author Rusben Guzman
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML private Button btnCrearEvento;
    @FXML private Button btnEliminarEvento;
    @FXML private Button btnVerificarCitas;
    @FXML private Button btnRegistrarUsusarios;
    @FXML private TableView<Appointment> tableCitas;
    @FXML TableColumn<Appointment, String> nombreColumn;
    @FXML TableColumn<Appointment, String> horaColumn;
    @FXML TableColumn<Appointment, String> motivoColumn;
    @FXML TableColumn<Appointment, String> apellidoColumn;
    // @FXML
    private DatePicker date;
    @FXML private VBox Vmenu;
    @FXML private AnchorPane pacientes_pane; 
    @FXML private Label nombre_p_label;
    @FXML private Label apellido_p_label;
    @FXML private Label cedula_p_label;
    @FXML private Label email_p_label;
    @FXML private Label telefono_p_label;
    
    ArrayList<Day> dayMedic;
    int currentDayMedicIndex = 0;
    ArrayList<Patient> patients;
    
    @FXML private ListView<String> list_alergias;
    @FXML private ListView<String> list_ant;
    
    // Componenetes de la tab diagnosticos
    @FXML private TextArea diagnostico_tx;
    
    @FXML private TableView<Treatment> table_tratamiento;
    @FXML private TableColumn<Treatment, String> tab_medicamento;
    @FXML private TableColumn<Treatment, String> tab_cantidad;
    @FXML private TableColumn<Treatment, String> tab_duracion;
    @FXML private TableColumn<Treatment, String> tab_frecuencia;
    
    @FXML private TextField medicamento_tx;
    @FXML private TextField cantidad_tx;
    @FXML private TextField duracion_tx;
    @FXML private TextField frecuencia_tx;
    
    @FXML private Button btn_guardard;
    @FXML private Button btn_addt;
    @FXML private Button btn_delt;
    
    
    @FXML private TabPane tabPane_info;
    @FXML private Tab pest_histM;
    @FXML private Tab pest_consulta;
    @FXML private Tab pest_paciente;
    @FXML private CheckBox chk_shared;
    
    @FXML
    private void crearEvento(ActionEvent event) {
        // Verifica si se selecciono algun item de la tableCitas
        int index = tableCitas.getSelectionModel().getSelectedIndex();
        System.out.println("Index tableCitas: " + index);
        System.out.println("Index tableCitas: " + this.currentDayMedicIndex);
        // Se verifica si el ususario ha seleccionado un slot de la tabla
        if (index == -1) {
            System.out.println("No ha seleccionado ningun item");
            return;
        }
        // Se verifica si el slot no esta lleno aun
        if (!tableCitas.getSelectionModel().getSelectedItem().getDescription().equals("")) {
            System.out.println("No puedes crear un evento en un evento existente");
            return;
        }
        
        
        // Si el dia no existe en la bd se crea y luego se crea el appointment
        if (currentDayMedicIndex  == -1 ) {
             System.out.println("El dia no existe. Se tiene que agregar a la BD"); 
             Day newDay = new Day();
             // Se obotiene la fecha del calendario, se hacel el parse a ISODate y se mete en el newDay
             Date currentDate = Date.from(this.date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
             DateTime currentISODate = new DateTime(currentDate); 
             newDay.setDate(currentISODate.toString());
             // El dia no esta lleno
             newDay.setFull(Boolean.FALSE);
             // Id del medico
             newDay.setMedicID("22824486");
            // Se crea un new dayAppointments 
            Appointment[] apps = new Appointment[0];
            newDay.setDayAppointments(apps);
            // Se crea el day en la BD
            newDay = HTTPRequest.addDay(newDay);
            // Se asgigna el ultimo indice del arreglo como dia actual
            currentDayMedicIndex = dayMedic.size();
            dayMedic.add(newDay);
            System.out.println("Dia añadiado con exito. indexDay: " + this.currentDayMedicIndex);
        }
        
        System.out.println("Se abre la ventana para agregar un elemento a la lista");
        crearCita cr = new crearCita();
        dayMedic.set(currentDayMedicIndex, cr.display(tableCitas,patients, dayMedic.get(currentDayMedicIndex)));
        Day dauAux = dayMedic.get(currentDayMedicIndex);
        if (cr.isCancel()) {
        } else { 
            Appointment appAux = dauAux.getDayAppointments()[dauAux.getDayAppointments().length - 1];
            appAux.setSlot(appAux.getStart() + " - " + appAux.getEnd());
            tableCitas.getItems().set(index, appAux);
            System.out.println("Despues de crear el evento: " + appAux.getId() );
            System.out.println("IndexItem: " + tableCitas.getSelectionModel().getSelectedIndex());
        }
    }
    
    @FXML
    private void eliminarEvento(ActionEvent event) throws IOException{
        ObservableList<Appointment> citaSelect, allProductos;
        // Se obtienen todos los slot de el dia
        allProductos = tableCitas.getItems();  
        //  Se obtiene el slot seleccionado
        citaSelect = tableCitas.getSelectionModel().getSelectedItems(); 
        // Si no se selecciono ninguna cita retorna 
        if (citaSelect.isEmpty()) {
            System.out.println("No se ha seleccionado ninguna cita");
        } else {
            // Se obtiene la cita seleccionada para eliminar
            Appointment cita = citaSelect.get(0);
            if (cita.getId() == null || cita.getId().equals("")) {
                System.out.println("Cita no existe");
            } else {
                // Se crea una cita auxiliar que solo guarde la hora de la cita y vacie los otros campos
                Appointment np = new Appointment(); 
                np.setStart(cita.getStart());
                np.setSlot(cita.getSlot());
                np.setPatientName("");
                np.setDescription("");
                // POST: eleminar una cita del dia
                dayMedic.set(currentDayMedicIndex, HTTPRequest.deleteAppointment(dayMedic.get(currentDayMedicIndex).getId(), cita.getId()));
                allProductos.set(tableCitas.getSelectionModel().getSelectedIndex(), np);
            }
        }
  }
    
    @FXML
    private void agregarPaciente(ActionEvent event) {
        System.out.println("Se abre la ventana para agregar un paciente a la BD");
        Custom_control_agregarPacienteController ap = new Custom_control_agregarPacienteController();
        ap.display(patients);
        dayMedic = new ArrayList<Day>(Arrays.asList(HTTPRequest.getDays("22824486")));
        Date dte = new Date(2016,06,02,00,00,00);
        Calendar c = Calendar.getInstance();
        c.setTime(dte);
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        vaciarAppointmentsInit(c, dateFormat);
        InsertarAppointments(c, dateFormat);
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        disablePest();
        // Obtenemos los deias del medico y los mete en el arreglo dayMedic
        // NOTA: hay que hacer la pantalla login para obtener los dias del medico seleccionado
        dayMedic = new ArrayList<Day>(Arrays.asList(HTTPRequest.getDays("22824486")));
        // Se obtienen todos los pacientes de la bd
        patients = new ArrayList<Patient>(Arrays.asList(HTTPRequest.getAllPatients()));
        //System.out.println(patients[0].getEmail());
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
        vaciarAppointmentsInit(c, dateFormat);
        InsertarAppointments(c, dateFormat);

        // Inicializacion de las columnas de la tabla
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        horaColumn.setCellValueFactory(new PropertyValueFactory<>("slot"));
        motivoColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        // Manejador de los eventos de el calendario
        //GET: obtener todos los dias y ponerlos en un arreglo de appointment
        dp.setOnAction(e -> {
                // Se vacia la tabla para luego llenarla con los appoinmets correespondientes
                vaciarAppointmentsSelect(c, dateFormat);            
                InsertarAppointments(c, dateFormat);
                });
        //------------------- Manejador del evento cuando se oprime una fila de la tableCitas ------------
       tableCitas.setRowFactory( tv -> {
            TableRow<Appointment> row = new TableRow<>();
            
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    Appointment rowData = row.getItem();
                    ObservableList<String> list1 = FXCollections.observableArrayList();
                    ObservableList<String> list2 = FXCollections.observableArrayList();
                    list_alergias.setItems(list1);
                        list_ant.setItems(list2);
                    if (rowData.getPatientName().equals("")) {
                        nombre_p_label.setText("");
                        apellido_p_label.setText("");
                        cedula_p_label.setText("");
                        email_p_label.setText("");
                        telefono_p_label.setText("");
                        disablePest();
                        return;
                    } 
                    enablePest();
                    // Se busca el paciente de la fila seleccionada
                    System.out.println();
                    int i;
                    for (i = 0; i < patients.size() ; i++) {
                        //System.out.println("patient: " + patients[i].getId() + " row: " + rowData.getPatientID());
                        if (patients.get(i).getPatientID().equals(rowData.getPatientID()))
                            break;
                    }
                    
                    nombre_p_label.setText(patients.get(i).getName());
                    apellido_p_label.setText(patients.get(i).getLastName());
                    cedula_p_label.setText(patients.get(i).getPatientID());
                    email_p_label.setText(patients.get(i).getEmail());
                    telefono_p_label.setText(patients.get(i).getPhoneNumber());
                    // Se recorren las alergias para mostrarlas en la informacion del paciente 
                    for (int j = 0; j < patients.get(i).getAllergies().length ; j++) {
                        list_alergias.getItems().add(patients.get(i).getAllergies()[j]);
                    }
                    for (int j = 0; j < patients.get(i).getMedicalBackgrounds().length ; j++) {
                        list_ant.getItems().add(patients.get(i).getMedicalBackgrounds()[j]);
                    }
                    // Si tiene diagnostico en la bd se muestra la informacion y se deshabilita las pestañas
                    
                }
                
                
                
                
            });
            return row;
        });
    
        //----------------------- Inicializacion de los componenetes de diagnosticos -------------------------------
        tab_medicamento.setCellValueFactory(new PropertyValueFactory<>("medication"));
        tab_cantidad.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tab_duracion.setCellValueFactory(new PropertyValueFactory<>("duration"));
        tab_frecuencia.setCellValueFactory(new PropertyValueFactory<>("frequency"));
       
        // Manejadores de eventos de los botones
        btn_guardard.setOnAction(e -> {
            // Se asignan los tratamientos al arreglo
            ObservableList<Treatment> listt = table_tratamiento.getItems();
            ObservableList<Appointment> lists = tableCitas.getSelectionModel().getSelectedItems();
            Appointment a = lists.get(0);
            Treatment[] t = new Treatment[listt.size()]; 
            for (int i = 0; i < listt.size() ; i++) {
                t[i] = listt.get(i);
            }
            
            Diagnostic d = new Diagnostic(localdate2ISO(date.getValue()),diagnostico_tx.getText(),t,
                                           chk_shared.isSelected(),"22824486"); 
            
            String s = null;
            int i;
            for (i = 0; i < patients.size() ; i++) {
                if (a.getPatientID().equals(patients.get(i).getPatientID())) {
                    s = patients.get(i).getId();
                    break;
                }
            }
            
            //Guardar el diagnosico en la lBD y en la estructura del programa
             patients.set(i, HTTPRequest.addDiagnostic(s, d));
             disableHist();
        });
        
        btn_addt.setOnAction(e -> {
            // validar los datos de los imputs
            if ((medicamento_tx.getText().equals("") || cantidad_tx.getText().equals("") ||
                 duracion_tx.getText().equals("") || frecuencia_tx.getText().equals("")))
                return;
            Treatment t = new Treatment(medicamento_tx.getText(), cantidad_tx.getText(),
                                        duracion_tx.getText(),frecuencia_tx.getText());
            table_tratamiento.getItems().add(t);
        });
        btn_delt.setOnAction(e -> {
            // validar los datos de los imputs
            ObservableList l,s;
            l = table_tratamiento.getItems();
            s = table_tratamiento.getSelectionModel().getSelectedItems();
            l.remove(s.get(0));
        });
    }
    
    public void vaciarAppointmentsInit(Calendar c, DateFormat dateFormat){
                    // Se vacia la tabla para luego llenarla con los appoinmets correespondientes
                    Date newdte = new Date(2016,06,02,00,00,00);
                    Calendar calen = Calendar.getInstance();
                    c.setTime(newdte);
                    DateFormat dateformt = new SimpleDateFormat("HH:mm");

                    for (int l = 0;l < 24; l++) {
                        Appointment a = new Appointment();
                        a.setStart(dateFormat.format(c.getTime()));
                        a.setSlot(dateFormat.format(c.getTime()) + " - ");
                        c.add(Calendar.HOUR_OF_DAY, 1);
                        a.setEnd(dateFormat.format(c.getTime()));
                        a.setSlot(a.getSlot()+dateFormat.format(c.getTime()));
                        this.tableCitas.getItems().add(a);
                    }
            }

    public void vaciarAppointmentsSelect(Calendar c, DateFormat dateFormat){
                    // Se vacia la tabla para luego llenarla con los appoinmets correespondientes
                    Date newdte = new Date(2016,06,02,00,00,00);
                    Calendar calen = Calendar.getInstance();
                    c.setTime(newdte);
                    DateFormat dateformt = new SimpleDateFormat("HH:mm");

                    for (int l = 0;l < 24; l++) {
                        Appointment a = new Appointment();
                        a.setStart(dateFormat.format(c.getTime()));
                        a.setSlot(dateFormat.format(c.getTime()) + " - ");
                        c.add(Calendar.HOUR_OF_DAY, 1);
                        a.setEnd(dateFormat.format(c.getTime()));
                        a.setSlot(a.getSlot()+dateFormat.format(c.getTime()));
                        this.tableCitas.getItems().set(l,a);
                    }
        
    }
    
    public void InsertarAppointments(Calendar c, DateFormat dateFormat){    
               int i,j;
                
               if (dayMedic.isEmpty()){
                   System.out.println("dayMedic esta vacio");
                   currentDayMedicIndex = -1;
                   return;
               }
               else{
                   System.out.println(dayMedic.get(0).getDate());
               }
               
               System.out.println("Tamaño del arreglos days: " + dayMedic.size());
               
               Gson gson = new GsonBuilder().create();
               for (int k = 0; k < dayMedic.size(); k++) {
                   System.out.println("Day en index: " + k + " is " + gson.toJson(dayMedic.get(k)));
               }
               
               DateTime dt;
               // Formato de comparacion de las fehcas
               DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
               // Se obtiene la fecha que se selecciono en el calendario
               String selectedDateString = dtf.format(Date.from(this.date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
               
               System.out.println("Fecha seleccionada del calendario: " + selectedDateString); 
               
                //Busco el dia en el arreglo segun el dia seleccionado en el calendario 
               for (i=0; (i < dayMedic.size()) ;i++) {
                   // Se pasa al siguiente dia
                   dt = new DateTime(dayMedic.get(i).getDate());
                   //System.out.println(dtf.format(dt.toDate()));
                   //Si encuentra el dia en el arreglo hace break y se obtiene la posicion del dia con "i"
                   String str = dtf.format(dt.toDate());
                   System.out.println("Fecha en revision: " + str);
                   if ( str.equals(selectedDateString) ){
                       System.out.println("Index en el array day cuando encuentra: " + i);
                       System.out.println("Esta fecha es igual: " + dtf.format(dt.toDate()));
                       currentDayMedicIndex = i;
                       break;
                   }
               }
               // Si no encuentre el dia en el arreglo retorna
               if ( i == dayMedic.size() ) {
                   currentDayMedicIndex = -1;
                   System.out.println("El dia no existe o no tiene citas");
                   return;
               }
               // Se recorren los appointments y se insertan en su slot correspondiente de la tabla
               for (j=0;j<dayMedic.get(i).getDayAppointments().length;j++){
                   
                   Appointment aux = dayMedic.get(i).getDayAppointmentsPos(j);
                   
                   // Se la asigna el slot al dia
                   String strStart = aux.getStart();
                   
                   aux.setSlot(aux.getStart() + " - " + aux.getEnd());
                 
                   // Se verifica si es el slot correspondiente y se inserta ç
                   String strSlot;
                   for (int k = 0;k < tableCitas.getItems().size();k++) {
                       strSlot = tableCitas.getItems().get(k).getSlot();
                       if (aux.getSlot().equals(strSlot)) {
                           // Si es una hora de descanso o el doctor no va a estar
                           if (!aux.getEventType().equals("Consulta")){
                               aux.setDescription(aux.getDescription());
                           }
                           //aux.print();
                           // Se crea un nuevo appointemenet y se inserta en la posicion del slot
                           Appointment appSlot = new Appointment(aux.getId(),aux.getStart(),aux.getEnd(),
                           aux.getEventType(),aux.getPatientID(),aux.getPatientName(),aux.getDescription()
                           ,aux.getPatientLastName(),aux.getStart(),aux.getEnd());
                           appSlot.setSlot(aux.getSlot());
                           tableCitas.getItems().set(k, appSlot);
                           break;
                       }
                   }
               }
    
    }
    
    public void disablePest() {
        SingleSelectionModel<Tab> selectionModel = tabPane_info.getSelectionModel();
        pest_histM.setDisable(true);
        pest_consulta.setDisable(true);
        diagnostico_tx.setDisable(true);
        diagnostico_tx.setText("");
        medicamento_tx.setText("");
        cantidad_tx.setText("");
        duracion_tx.setText("");
        frecuencia_tx.setText("");
        ObservableList<Treatment> list1 = FXCollections.observableArrayList();
        table_tratamiento.setItems(list1);
        selectionModel.select(pest_paciente);
    }
    
    public void disableHist() {
        pest_histM.setDisable(true);
        pest_consulta.setDisable(true);
        diagnostico_tx.setDisable(true);
    }
    
    public void enablePest() {
        pest_histM.setDisable(false);
        pest_consulta.setDisable(false);
        diagnostico_tx.setDisable(false);
    }
    
    public LocalDate ISO2LocalDate(String iso) {
        DateTime isodt = new DateTime(iso);
        Date dt = isodt.toDate();
        LocalDate local = dt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return local;
    }
    
    public String localdate2ISO(LocalDate local) {
        Date d = Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant());
        DateTime dt = new DateTime(d);
        return dt.toString();
    }
}
