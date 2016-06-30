/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicoapp;

// Clase TreatmentResult: Contiene la información del feedback de un tratamiento.

public class TreatmentResult {
    
    private String _id;
    private int rating;

    public TreatmentResult(String _id, int rating) {
        this._id = _id;
        this.rating = rating;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "TreatmentResult{" + "_id=" + _id + ", rating=" + rating + '}';
    }
    
}
