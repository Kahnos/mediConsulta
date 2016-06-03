package medicoapp;

import java.util.Date;

/**
 * Clase Day: Contiene la informacion del dia de un medico 
 * @author Los Appeadores 
 */
public class Day {
    
    private String date;
    private String medicID;
    private Boolean full;
    private Appointment[] dayAppointments;
    
    // Constructores 

    public Day() {
        this.date = "";
        this.medicID = "";
        this.full = false;
        this.dayAppointments = new Appointment[50];
    }

    public Day(String date, String medicID, Boolean full) {
        this.date = date;
        this.medicID = medicID;
        this.full = full;
        this.dayAppointments = new Appointment[50];
    }

    // Getters y Setters
    
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMedicID() {
        return medicID;
    }

    public void setMedicID(String medicID) {
        this.medicID = medicID;
    }

    public Boolean getFull() {
        return full;
    }

    public void setFull(Boolean full) {
        this.full = full;
    }

    public Appointment[] getDayAppointments() {
        return dayAppointments;
    }

    public void setDayAppointments(Appointment[] dayAppointments) {
        this.dayAppointments = dayAppointments;
    }
    
    // Funciones adicionales
    
    /*
     * getDayAppointmentPos: devuelve la cita en la posición pos en el arreglo de citas 
     * @pos: posición de la cita en el arreglo
     */
    public Appointment getDayAppointmentsPos(int pos) {
        return this.dayAppointments[pos];
    }
    
}
