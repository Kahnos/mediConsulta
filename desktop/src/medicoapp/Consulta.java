/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicoapp;

/**
 *
 * @author Rusben Guzman
 */
public class Consulta {
    
    private String fecha;
    private String motivo;
    private String diagnosticID;
    
    public void Consulta () {
        this.fecha = "";
        this.motivo = "";
        this.diagnosticID = "";
    }
    
    public void Consulta (String fecha, String motivo, String diagnosticID) {
        this.fecha = fecha;
        this.motivo = motivo;
        this.diagnosticID = diagnosticID;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDiagnosticID() {
        return diagnosticID;
    }

    public void setDiagnosticID(String diagnosticID) {
        this.diagnosticID = diagnosticID;
    }
    
    @Override
    public String toString(){
        return "{ fecha=" + this.fecha + ", motivo=" + this.motivo + ", diagnosticID="+ this.diagnosticID + "}";
    }
    
}
