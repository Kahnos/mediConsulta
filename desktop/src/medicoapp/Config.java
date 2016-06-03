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
    
    private String id;
    private String medicID;
    private dayConfig[] dayConfigs;

    public Config(String id, String medicID, dayConfig[] dayConfigs) {
        this.id = id;
        this.medicID = medicID;
        this.dayConfigs = dayConfigs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    
    // Clase que contiene la configuración de un día específico. Subclase de Config.
    public class dayConfig {
        
        private String day;
        private String scheduleStart;
        private String scheduleEnd;
        private String slotLength;

        public dayConfig(String day, String scheduleStart, String scheduleEnd, String slotLength) {
            this.day = day;
            this.scheduleStart = scheduleStart;
            this.scheduleEnd = scheduleEnd;
            this.slotLength = slotLength;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getScheduleStart() {
            return scheduleStart;
        }

        public void setScheduleStart(String scheduleStart) {
            this.scheduleStart = scheduleStart;
        }

        public String getScheduleEnd() {
            return scheduleEnd;
        }

        public void setScheduleEnd(String scheduleEnd) {
            this.scheduleEnd = scheduleEnd;
        }

        public String getSlotLength() {
            return slotLength;
        }

        public void setSlotLength(String slotLength) {
            this.slotLength = slotLength;
        }
        
    }
}