/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicoapp;

/**
 * Contiene la información de un usuario del sistema.
 * @author Kahnos <josed1305@gmail.com>
 */
public class User {
    
    private String _id;
    private String userID;
    private String password;
    private String userType;
    private String name;
    private String lastName;
    private String birthdate;
    private String email;
    private String phoneNumber;
    private String sex;
    private String medicID;

    public User(String _id, String userID, String password, String userType, String name, String lastName, String birthdate,
            String email, String phoneNumber, String sex, String medicID) {
        this._id = _id;
        this.userID = userID;
        this.password = password;
        this.userType = userType;
        this.name = name;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.medicID = medicID;
    }

    public User(String userID, String password, String userType, String name, String lastName, String birthdate, String email,
            String phoneNumber, String sex) {
        this.userID = userID;
        this.password = password;
        this.userType = userType;
        this.name = name;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
    }

    public User(String userID, String password, String userType, String name, String lastName, String birthdate, String email,
            String phoneNumber, String sex, String medicID) {
        this.userID = userID;
        this.password = password;
        this.userType = userType;
        this.name = name;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.medicID = medicID;
    }
    
    // Getters

    public String getId() {
        return _id;
    }

    public String getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSex() {
        return sex;
    }

    public String getMedicID() {
        return medicID;
    }
    
    // Setters

    public void setId(String _id) {
        this._id = _id;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setMedicID(String medicID) {
        this.medicID = medicID;
    }

    @Override
    public String toString() {
        return "User{" + "_id=" + _id + ", userID=" + userID + ", password=" + password + ", userType=" + userType + ", name=" + name + ", lastName=" + lastName + ", birthdate=" + birthdate + ", email=" + email + ", phoneNumber=" + phoneNumber + ", sex=" + sex + ", medicID=" + medicID + '}';
    }
    
}















