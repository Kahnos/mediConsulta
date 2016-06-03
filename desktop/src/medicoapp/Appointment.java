package medicoapp;

import java.util.Date;


/**
 * Clase Appointment: contiene la información de las citas, será una agregación de day
 * @author los Appeadores
 */
public class Appointment implements Comparable<Appointment>{
    
    private Date start; 
    private Date end; 
    private String slot;
    private String eventType;
    private String patientID;
    private String patientName;
    private String patientLastName;
    private String description;
    
    // Constructores

    public Appointment() {
        this.start = new Date(); 
        this.end = new Date(); 
        this.eventType = "";
        this.patientID = "";
        this.patientName = "";
        this.patientLastName = "";
        this.description = "";
        this.slot = "";
        
    }

    public Appointment(Date start, Date end, String eventType, String patientID, String patientName, String description, String patientLastName, String startString, String endString) {
        this.start = start;
        this.end = end;
        this.eventType = eventType;
        this.patientID = patientID;
        this.patientName = patientName;
        this.patientLastName = patientLastName;
        this.description = description;
        this.slot = slot;
    }
    
    // Getters

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public String getEventType() {
        return eventType;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    
    
    public String getDescription() {
        return description;
    }

    public String getSlot() {
        return slot;
    }

    
    
    // Setters 

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }
    
    
    @Override 
    public int compareTo(Appointment a) {
        return getStart().compareTo(a.getStart());
    }
}
