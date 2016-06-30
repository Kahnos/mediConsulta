/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicoapp;

/**
 * Clase Config: Contiene la configuración de un médico.
 * @author Kahnos <josed1305@gmail.com>
 */
public class Config {
    
    private String _id;
    private String medicID;
    private dayConfig[] dayConfigs;

    public Config(String _id, String medicID, dayConfig[] dayConfigs) {
        this._id = _id;
        this.medicID = medicID;
        this.dayConfigs = dayConfigs;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getMedicID() {
        return medicID;
    }

    public void setMedicID(String medicID) {
        this.medicID = medicID;
    }

    public dayConfig[] getDayConfigs() {
        return dayConfigs;
    }

    public void setDayConfigs(dayConfig[] dayConfigs) {
        this.dayConfigs = dayConfigs;
    }

    @Override
    public String toString() {
        return "Config{" + "_id=" + _id + ", medicID=" + medicID + ", dayConfigs=" + dayConfigs + '}';
    }
    
}