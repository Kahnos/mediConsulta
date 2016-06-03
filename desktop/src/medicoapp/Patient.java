/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicoapp;

import java.util.Date;

/**
 * Clase Patient: Contiene la información de un Patient.
 * @author Kahnos <josed1305@gmail.com>
 */
public class Patient {
    
    private String id;
    private String patientID;
    private String name;
    private Date birthdate;
    private String email;
    private String phoneNumber;
    private String sex;
    private Integer weight;
    private Integer height;
    private String[] medicalBackgrounds;
    private String[] allergies;
    private Diagnostic[] diagnostics;

    public Patient(String id, String patientID, String name, Date birthdate, String email, String phoneNumber, String sex, Integer weight, Integer height, String[] medicalBackgrounds, String[] allergies, Diagnostic[] diagnostics) {
        this.id = id;
        this.patientID = patientID;
        this.name = name;
        this.birthdate = birthdate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.weight = weight;
        this.height = height;
        this.medicalBackgrounds = medicalBackgrounds;
        this.allergies = allergies;
        this.diagnostics = diagnostics;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String[] getMedicalBackgrounds() {
        return medicalBackgrounds;
    }

    public void setMedicalBackgrounds(String[] medicalBackgrounds) {
        this.medicalBackgrounds = medicalBackgrounds;
    }

    public String[] getAllergies() {
        return allergies;
    }

    public void setAllergies(String[] allergies) {
        this.allergies = allergies;
    }

    public Diagnostic[] getDiagnostics() {
        return diagnostics;
    }

    public void setDiagnostics(Diagnostic[] diagnostics) {
        this.diagnostics = diagnostics;
    }
    
    // Clase Diagnostic: Contiene la información de un diagnóstico.
    public class Diagnostic {
        
        private Date date;
        private String diagnostic;
        private Treatment[] treatment;
        private TreatmentResult treatmentResult;

        public Diagnostic(Date date, String diagnostic, Treatment[] treatment, TreatmentResult treatmentResult) {
            this.date = date;
            this.diagnostic = diagnostic;
            this.treatment = treatment;
            this.treatmentResult = treatmentResult;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
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
        
        // Clase Treatment: Contiene la información de un tratamiento.
        public class Treatment {
            
            private String medication;
            private String quantity;
            private String duration;
            private String frequency;

            public Treatment(String medication, String quantity, String duration, String frequency) {
                this.medication = medication;
                this.quantity = quantity;
                this.duration = duration;
                this.frequency = frequency;
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
        
        }
        
        // Clase TreatmentResult: Contiene la información del feedback de un tratamiento.
        public class TreatmentResult {
            
            private int rating;

            public TreatmentResult(int rating) {
                this.rating = rating;
            }

            public int getRating() {
                return rating;
            }

            public void setRating(int rating) {
                this.rating = rating;
            }
            
        }
    }
    
}
