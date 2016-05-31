/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicoapp;

import java.io.IOException;
import java.util.ArrayList;
import static java.util.Collections.list;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    
    @FXML private TextField tf_nombre;
    @FXML private TextField tf_hora;
    @FXML private TextField tf_motivo;
    
    @FXML private Button btn_aceptar;
    @FXML private Button btn_cancelar;
    @FXML private Pane mainPanel;
    
    
    
    public crearCita() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("custom_control_crearCita.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
       //display(table);
    }

/*    @FXML 
    //@SuppressWarnings("empty-statement")
    public void addItemOrdered(String nombre, String hora, String motivo, ListView<Cita> listCitas){
        int i = 0;
        Cita nuevaCita = new Cita();
        nuevaCita.setAll(nombre, hora, motivo);
        ArrayList<Cita> citas = new ArrayList<>(listCitas.getItems());
        
        
        if (listCitas.getItems().isEmpty()){
            listCitas.getItems().add(nuevaCita);
            return;
        }
        
        for (Cita c: citas) { 
              if (c.getHora().compareTo(hora) > 0)
            {
                listCitas.getItems().add(i,nuevaCita);
                return;
            } 
            i++; 
        }
        
        listCitas.getItems().add(i,nuevaCita);
    }
  */  
    @FXML
    public void display (TableView<Cita> table)
    {
       Stage window = new Stage(); 
       window.initModality(Modality.APPLICATION_MODAL);
       window.setTitle("Crear evento");
       window.setMinWidth(250);
       
       btn_aceptar.setOnAction((ActionEvent e) -> {
                 if (tf_nombre.getText().equals("") || 
            tf_hora.getText().equals("") ||
            tf_motivo.getText().equals("")) { 
            System.out.println("tiene que llenar los campos para poder insertar en la tablaa");
            return;
        }
        Cita np = new Cita();
        np.setNombre(tf_nombre.getText());
        np.setHora(tf_hora.getText());
        np.setMotivo(tf_motivo.getText());
        table.getItems().add(np);
        tf_nombre.clear();
        tf_hora.clear();
        tf_motivo.clear();  
//       addItemOrdered(tf_nombre.getText(),tf_hora.getText(),tf_motivo.getText(),list);
       });
       
       btn_cancelar.setOnAction((ActionEvent e) -> {
           window.close();
       });
       VBox vb = new VBox();
       vb.getChildren().addAll(mainPanel);
       vb.setAlignment(Pos.CENTER);
       Scene scene = new Scene(vb);
       window.setScene(scene);
       window.showAndWait();
    
    }
   
    public  String getTf_nombre() {
        return tf_nombre.getText();
    }
   
    public String getTf_hora() {
        return tf_hora.getText();
    }
   
    public String getTf_motivo() {
        return tf_motivo.getText();
    }
   
    public void setTf_nombre(String nombre) {
        this.tf_nombre.setText(nombre);
    }
   
    public void setTf_hora(String hora) {
        this.tf_hora.setText(hora);
    }
  
    public void setTf_motivo(String motivo) {
        this.tf_motivo.setText(motivo);
    }

}
