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
    public void display (TableView<Appointment> table)
    {
       
       // Se declara una window 
       Stage window = new Stage(); 
       window.initModality(Modality.APPLICATION_MODAL);
       window.setTitle("mediConsulta - Crear evento");
       window.setMinWidth(250);
     
        // Se inicializan los elementos de la comboBox
       patient_cb.getItems().addAll("Rosben","Lobny","Doniel","Josus");

        //Se crean los manejadores de eventos de los controles
        btn_aceptar.setOnAction((ActionEvent e) -> {
                 if (motivo_txta.getText().equals("") || 
                     patient_cb.getValue().equals("") ) { 
                        System.out.println("Tiene que llenar los campos para poder insertar en la tablaa");
                        return;
                }
                //-----------------------------------------------
                ObservableList<Appointment> citaSelect, allProductos;
                allProductos = table.getItems();
                citaSelect = table.getSelectionModel().getSelectedItems();
                Appointment np = new Appointment();
                np.setStart(citaSelect.get(0).getStart());
                np.setSlot(citaSelect.get(0).getSlot());
                np.setPatientName(patient_cb.getValue());
                np.setDescription(motivo_txta.getText());
                allProductos.set(table.getSelectionModel().getSelectedIndex(), np);
                
                motivo_txta.clear();  
                window.close();
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
