/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicoapp;

// Clase Treatment: Contiene la información de un tratamiento.

public class Treatment {
    
    private String _id;
    private String medication;
    private String quantity;
    private String duration;
    private String frequency;

    public Treatment(String _id, String medication, String quantity, String duration, String frequency) {
        this._id = _id;
        this.medication = medication;
        this.quantity = quantity;
        this.duration = duration;
        this.frequency = frequency;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "Treatment{" + "_id=" + _id + ", medication=" + medication + ", quantity=" + quantity + ", duration=" + duration + ", frequency=" + frequency + '}';
    }
    
}
