package medicoapp;

/**
 * Clase cita, con este objeto se extraeran todos los datos desde el JSON
 * 
 */
public class Cita {
    private String patientName;
    private String start;
    private String description;
    private String patientLastName;

    // Constructores
    public Cita() {
        this.patientName = "";
        this.start = "";
        this.description = "";
        this.patientLastName = "";
    }

    public Cita(String patientName, String start, String description, String patientLastname) {
        this.patientName = patientName;
        this.start = start;
        this.description = description;
        this.patientLastName = patientLastName;
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

    public String getPatientLastName() {
        return patientLastName;
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

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }
    
    public void setAll(String patientName, String start, String description, String patientLastName){
        this.patientName = patientName;
        this.start = start;
        this.description = description;
        this.patientLastName = patientLastName;
    }

    
}
