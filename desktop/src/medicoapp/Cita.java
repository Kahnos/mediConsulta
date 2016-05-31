package medicoapp;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Sample custom control hosting a text field and a button.
 */
public class Cita {
    private String patientName;
    private String start;
    private String description;

    public Cita() {
        this.patientName = "";
        this.start = "";
        this.description = "";
    }

    public Cita(String patientName, String start, String description) {
        this.patientName = patientName;
        this.start = start;
        this.description = description;
    }
    
    
    //Metodos get
    public String getNombre() {
        return patientName;
    }

    public String getHora() {
        return start;
    }
    
    public String getMotivo() {
        return description;
    }
    
    
    //Metodos set
    public void setNombre(String patientName) {
        this.patientName = patientName;
    }

    public void setHora(String start) {
        this.start = start;
    }

    
    public void setMotivo(String description) {    
        this.description = description;
    }

    public void setAll(String patientName, String start, String description){
        this.patientName = patientName;
        this.start = start;
        this.description = description;
    }

}
