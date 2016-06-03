/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicoapp;

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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML
    private DatePicker Calendario;
    @FXML
    private VBox Vmenu;
    
    
    @FXML
    private void crearEvento(ActionEvent event) {
        System.out.println("Se agrega un elemento a la lista");
        crearCita cr = new crearCita();
        cr.display(tableCitas);
    }
    
    
    @FXML
    private void eliminarEvento(ActionEvent event) throws IOException{
            ObservableList<Appointment> productosSelect, allProductos;
            allProductos = tableCitas.getItems();
            productosSelect = tableCitas.getSelectionModel().getSelectedItems();
           // productosSelect.forEach(allProductos::remove);
                Appointment np = new Appointment();
                np.setStart(productosSelect.get(0).getStart());
                np.setSlot(productosSelect.get(0).getSlot());
                np.setPatientName("");
                np.setDescription("");
                allProductos.set(tableCitas.getSelectionModel().getSelectedIndex(), np);
}
   
    @FXML
    private void verificarSolicitudHTTP(ActionEvent event) {
        HTTPRequest.deleteAppointment( "5751b61187f7efac09c03f8d", "5751b61187f7efac09c03f8e");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicia el calendario con la fecha actual
        Calendario.setValue(LocalDate.now());
        // Inicialización de las filas por bloque de horas en la tabla
        tableCitas.setEditable(true);
        Date date = new Date(2016,06,02,00,00,00);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
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
        // Manejador de los eventos de edicion de las tablas
        
        
    }  
    
}
