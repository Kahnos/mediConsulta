package medicoapp;

/**
 * Clase Patient: Contiene la información de un Patient.
 * @author Kahnos <josed1305@gmail.com>
 */
public class Patient {
    
    private String _id;
    private String patientID;
    private String name;
    private String lastName;
    private String birthdate;
    private String email;
    private String phoneNumber;
    private String sex;
    private double weight;
    private double height;
    private String[] medicalBackgrounds;
    private String[] allergies;
    private Diagnostic[] diagnostics;

    public Patient() {
        this._id = "";
        this.patientID = "";
        this.name = "";
        this.lastName = "";
        this.birthdate = "";
        this.email = "";
        this.phoneNumber = "";
        this.sex = "";
        this.weight = 0;
        this.height = 0;
    }
    
    public Patient(String _id, String patientID, String name, String birthdate, String email, String phoneNumber, String sex,
            Integer weight, Integer height, String[] medicalBackgrounds, String[] allergies, Diagnostic[] diagnostics) {
        this._id = _id;
        this.patientID = patientID;
        this.name = name;
        this.lastName = lastName;
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
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
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

    public String getLastName() {
        return lastName;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
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

    @Override
    public String toString() {
        return "Patient{" + "_id=" + _id + ", patientID=" + patientID + ", name=" + name + ", lastName=" + lastName + ", birthdate=" + birthdate + ", email=" + email + ", phoneNumber=" + phoneNumber + ", sex=" + sex + ", weight=" + weight + ", height=" + height + ", medicalBackgrounds=" + medicalBackgrounds + ", allergies=" + allergies + ", diagnostics=" + diagnostics + '}';
    }
   
}
