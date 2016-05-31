/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicoapp;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private TableView<Cita> tableCitas;
    @FXML
    TableColumn<Cita, String> nombreColumn;
    @FXML
    TableColumn<Cita, String> horaColumn;
    @FXML
    TableColumn<Cita, String> motivoColumn;
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
            ObservableList<Cita> productosSelect, allProductos;
            allProductos = tableCitas.getItems();
            productosSelect = tableCitas.getSelectionModel().getSelectedItems();
            productosSelect.forEach(allProductos::remove);
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicia el calendario con la fecha actual
        Calendario.setValue(LocalDate.now());
        // Inicializacion de las columnas de la tabla
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        horaColumn.setCellValueFactory(new PropertyValueFactory<>("hora"));
        motivoColumn.setCellValueFactory(new PropertyValueFactory<>("motivo"));
        // Manejador de los eventos de edicion de las tablas
        
        
    }  
    
}
