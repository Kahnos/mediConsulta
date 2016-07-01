/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicoapp;

// Clase Diagnostic: Contiene la información de un diagnóstico.

public class Diagnostic {
    
    private String _id;
    private String date;
    private String diagnostic;
    private Treatment[] treatment;
    private TreatmentResult treatmentResult;
    private boolean shared;
    private String medicID;
    private String appointmentID;

    public Diagnostic(String _id, String date, String diagnostic, Treatment[] treatment, TreatmentResult treatmentResult) {
        this._id = _id;
        this.date = date;
        this.diagnostic = diagnostic;
        this.treatment = treatment;
        this.treatmentResult = treatmentResult;
    }

    public Diagnostic(String _id, String date, String diagnostic, Treatment[] treatment, TreatmentResult treatmentResult, boolean shared, String medicID) {
        this._id = _id;
        this.date = date;
        this.diagnostic = diagnostic;
        this.treatment = treatment;
        this.treatmentResult = treatmentResult;
        this.shared = shared;
        this.medicID = medicID;
    }
    
    public Diagnostic(String date, String diagnostic, Treatment[] treatment, TreatmentResult treatmentResult, boolean shared, String medicID) {
        this.date = date;
        this.diagnostic = diagnostic;
        this.treatment = treatment;
        this.treatmentResult = treatmentResult;
        this.shared = shared;
        this.medicID = medicID;
    }

    public Diagnostic(String date, String diagnostic, Treatment[] treatment, boolean shared, String medicID, String appointmentID) {
        this.date = date;
        this.diagnostic = diagnostic;
        this.treatment = treatment;
        this.shared = shared;
        this.medicID = medicID;
        this.appointmentID = appointmentID;
    }
    
    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public Treatment[] getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment[] treatment) {
        this.treatment = treatment;
    }

    public TreatmentResult getTreatmentResult() {
        return treatmentResult;
    }

    public void setTreatmentResult(TreatmentResult treatmentResult) {
        this.treatmentResult = treatmentResult;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public String getMedicID() {
        return medicID;
    }

    public void setMedicID(String medicID) {
        this.medicID = medicID;
    }

    @Override
    public String toString() {
        return "Diagnostic{" + "_id=" + _id + ", date=" + date + ", diagnostic=" + diagnostic + ", treatment=" + treatment + ", treatmentResult=" + treatmentResult + ", shared=" + shared + ", medicID=" + medicID + '}';
    }
    
}
