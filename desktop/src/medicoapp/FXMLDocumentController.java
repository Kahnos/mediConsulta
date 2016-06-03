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
    DatePicker date;
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
        // Se inicializa el calendario y el popup
        DatePicker dp = new DatePicker(LocalDate.now());
        DatePickerSkin datePickerSkin = new DatePickerSkin(dp);
        date = dp;
        Node popupContent = datePickerSkin.getPopupContent();
        Vmenu.getChildren().addAll(popupContent);
        //System.out.println("fecha :  "+dp.getValue());

        // Inicialización de las filas por bloque de horas en la tabla
        tableCitas.setEditable(true);
        Date date = new Date(2016,06,02,00,00,00);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        
        for (int i = 0;i < 24; i++) {
            Appointment a = new Appointment();
            a.setStart(c.getTime());
            a.setSlot(dateFormat.format(c.getTime()) + " - ");
            c.add(Calendar.HOUR_OF_DAY, 1);
            a.setEnd(c.getTime());
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
        
               
        
        });
        // Manejador del evento cuando se oprime una fila de la tableCitas
       tableCitas.setRowFactory( tv -> {
            TableRow<Appointment> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    Appointment rowData = row.getItem();
                    
                    /*
                    nombre_p_label.setText();
                    apellido_p_label.setText();
                    cedula_p_label.setText();
                    email_p_label.setText();
                    telefono_p_label.setText();
                    */
                    System.out.println(rowData + "\n" + rowData.getPatientName() + "\n" + rowData.getDescription());
                }
            });
            return row ;
        });

    }
    
}
