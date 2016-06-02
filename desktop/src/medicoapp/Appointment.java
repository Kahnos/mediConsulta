package medicoapp;

import java.util.Date;


/**
 * Clase Appointment: contiene la información de las citas, será una agregación de day
 * @author los Appeadores
 */
public class Appointment {
    
    private Date start; 
    private Date end; 
    private String eventType;
    private String patientID;
    private String patientName;
    private String description;
    
    // Constructores

    public Appointment() {
        this.start = new Date(); 
        this.end = new Date(); 
        this.eventType = "";
        this.patientID = "";
        this.patientName = "";
        this.description = "";
    }

    public Appointment(Date start, Date end, String eventType, String patientID, String patientName, String description) {
        this.start = start;
        this.end = end;
        this.eventType = eventType;
        this.patientID = patientID;
        this.patientName = patientName;
        this.description = description;
    }
    
    // Getters
    
    
    // Setters
    
    
    
    
}
