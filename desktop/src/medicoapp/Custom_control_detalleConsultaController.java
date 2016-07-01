/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicoapp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Libny
 */
public class Custom_control_detalleConsultaController extends VBox {
        // Panel principal
    @FXML private VBox mainPanel;
    @FXML private Label dateLabel;
    @FXML private TextArea diagnosticField;
    @FXML private TableView treatmentTable;
    @FXML private Diagnostic diagnostic;

    public Custom_control_detalleConsultaController() {
        // Carga el archivo FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("custom_control_login.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
          }
    }
    
    public void display(Diagnostic diagnostic){
        // Se declara una window 
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("mediConsulta - Detalle de consulta");
        window.setMinWidth(250);
        
        this.diagnostic = diagnostic;
        dateLabel.setText(diagnostic.getDate());
        diagnosticField.setText(diagnostic.getDiagnostic());
        
        ObservableList treatmentList = FXCollections.observableArrayList();
        for (int i = 0; i<diagnostic.getTreatment().length; i++){
            treatmentList.add(diagnostic.getTreatment()[i]);
        }
        treatmentTable.setItems(treatmentList);
        
        // Se crea un panel, se le asigna el panel a una scene y se le asigna la scene a la window
        VBox vb = new VBox();
        vb.getChildren().addAll(mainPanel);
        vb.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vb);
        window.setScene(scene);
        window.showAndWait();
    }

    
}
