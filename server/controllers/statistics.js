/*
* Archivo: controllers/statistic.js
* Provee los manejadores de solicitudes HTTP para calcular las estadísticas.
*/

var days = require('../models/days.js');

// GET - Retorna el promedio de ingreso por edad - Rangos de edad de 10 años, Mensual.
exports.getEntryByAge = function(req, res) {
    console.log('GET /statistics/:medicID/:month/entryByAge');
    
    days.find({ medicID: req.params.medicID }, function(err, days) {
        if ( err )
            return;

        var dayAux;
        var ageStatistic = [];
        var dayDate;
        var monthDate = new Date();
        var appointmentTotal = 0;
        monthDate.setMonth(req.params.month - 1);
        
        for (i = 0; i < days.length; i++) {
            dayAux = days[i];
            dayDate = new Date(dayAux.date);
            if (dayDate.getMonth() == monthDate.getMonth()) {        
                for (k = 0; k < dayAux.dayAppointments.length; k++) {
                    if (dayAux.dayAppointments[k].eventType.localeCompare('Consulta') == 0)
                        ++appointmentTotal;
                }
            }
        }

        for (i = 0; i < 10; i++) 
            ageStatistic[i] = 0;
            
        for (i = 0; i < days.length; i++) { 
            dayAux = days[i];
            dayDate = new Date(dayAux.date);

            if (dayDate.getMonth() == monthDate.getMonth()) {
                for (k = 0; k < dayAux.dayAppointments.length; k++) {
                    var appointment = dayAux.dayAppointments[k];
                    
                    if (appointment.eventType.localeCompare('Consulta') == 0) {
                        var patientAge = appointment.patientAge;
                        switch (true) {
                            case (patientAge > 0 &&
                                 patientAge <= 10):
                                ++ageStatistic[0];
                                break;

                            case (patientAge > 10 &&
                                 patientAge <= 20):
                                ++ageStatistic[1];
                                break;

                            case (patientAge > 20 &&
                                 patientAge <= 30):
                                ++ageStatistic[2];
                                break;

                            case (patientAge > 30 &&
                                 patientAge <= 40):
                                ++ageStatistic[3];
                                break;

                            case (patientAge > 40 &&
                                 patientAge <= 50):
                                ++ageStatistic[4];
                                break;

                            case (patientAge > 50 &&
                                 patientAge <= 60):
                                ++ageStatistic[5];
                                break;

                            case (patientAge > 60 &&
                                 patientAge <= 70):
                                ++ageStatistic[6];
                                break;

                            case (patientAge > 70 &&
                                 patientAge <= 80):
                                ++ageStatistic[7];
                                break;

                            case (patientAge > 80 &&
                                 patientAge <= 90):
                                ++ageStatistic[8];
                                break;

                            case (patientAge > 90 &&
                                 patientAge <= 100):
                                ++ageStatistic[9];
                                break;
                        }
                    }
                }
            }
        }
        
        for (i = 0; i < 10; i++) {
            ageStatistic[i] = ageStatistic[i] / appointmentTotal;
        }
                
        res.status(200).json(ageStatistic);
    });
};

// GET - Retorna el promedio de ingreso por sexo - Mensual.
exports.getEntryBySex = function(req, res) {
    console.log('GET /statistics/:medicID/:month/entryBySex');
    
    days.find({ medicID: req.params.medicID }, function(err, days) {
        if ( err )
            return;

        var dayAux;
        var sexStatistic = [];
        var dayDate;
        var monthDate = new Date();
        var appointmentTotal = 0;
        monthDate.setMonth(req.params.month - 1);
        
        for (i = 0; i < days.length; i++) {
            dayAux = days[i];
            dayDate = new Date(dayAux.date);
            if (dayDate.getMonth() == monthDate.getMonth()) {        
                for (k = 0; k < dayAux.dayAppointments.length; k++) {
                    if (dayAux.dayAppointments[k].eventType.localeCompare('Consulta') == 0)
                        ++appointmentTotal;
                }
            }
        }

        for (i = 0; i < 2; i++) 
            sexStatistic[i] = 0;
            
        for (i = 0; i < days.length; i++) { 
            dayAux = days[i];
            dayDate = new Date(dayAux.date);

            if (dayDate.getMonth() == monthDate.getMonth()) {
                for (k = 0; k < dayAux.dayAppointments.length; k++) {
                    var appointment = dayAux.dayAppointments[k];
                    
                    if (appointment.eventType.localeCompare('Consulta') == 0) {
                        var patientSex = appointment.patientSex;
                        switch (patientSex) {
                            case 'Masculino':
                                ++sexStatistic[0];
                                break;

                            case 'Femenino':
                                ++sexStatistic[1];
                                break;
                        }
                    }
                }
            }
        }
        
        for (i = 0; i < 2; i++) {
            sexStatistic[i] = sexStatistic[i] / appointmentTotal;
        }
                
        res.status(200).json(sexStatistic);
    });
};

// GET - Promedio de ingreso por segmentos del día - Mensual
exports.getEntryBySlot = function(req, res) {
    console.log('GET /statistics/:medicID/:month/entryBySlot');
    
    days.find({ medicID: req.params.medicID }, function(err, days) {
        if ( err )
            return;

        var dayAux;
        var slotStatistic = [];
        var dayDate;
        var monthDate = new Date();
        var appointmentTotal = 0;
        monthDate.setMonth(req.params.month - 1);
        
        for (i = 0; i < days.length; i++) {
            dayAux = days[i];
            dayDate = new Date(dayAux.date);
            if (dayDate.getMonth() == monthDate.getMonth()) {        
                for (k = 0; k < dayAux.dayAppointments.length; k++) {
                    if (dayAux.dayAppointments[k].eventType.localeCompare('Consulta') == 0)
                        ++appointmentTotal;
                }
            }
        }

        for (i = 0; i < 6; i++) 
            slotStatistic[i] = 0;
            
        for (i = 0; i < days.length; i++) { 
            dayAux = days[i];
            dayDate = new Date(dayAux.date);

            if (dayDate.getMonth() == monthDate.getMonth()) {
                for (k = 0; k < dayAux.dayAppointments.length; k++) {
                    var appointment = dayAux.dayAppointments[k];
                    
                    if (appointment.eventType.localeCompare('Consulta') == 0) {
                        var appointmentStart = appointment.start;
                        var appointmentStartTime = new Date();
                        appointmentStartTime.setTime(appointment.start.split(':')[0]);
                        var auxTime6am = new Date();
                        auxTime6am.setTime(6);
                        var auxTime9am = new Date();
                        auxTime9am.setTime(9);
                        var auxTime12pm = new Date();
                        auxTime12pm.setTime(12);
                        var auxTime3pm = new Date();
                        auxTime3pm.setTime(15);
                        var auxTime6pm = new Date();
                        auxTime6pm.setTime(18);

                        switch (true) {
                            case (appointmentStartTime < auxTime6am):
                                ++slotStatistic[0];
                                break;

                            case (appointmentStartTime >= auxTime6am &&
                                 appointmentStartTime < auxTime9am):
                                ++slotStatistic[1];
                                break;

                            case (appointmentStartTime >= auxTime9am &&
                                 appointmentStartTime < auxTime12pm):
                                ++slotStatistic[2];
                                break;

                            case (appointmentStartTime >= auxTime12pm &&
                                 appointmentStartTime < auxTime3pm):
                                ++slotStatistic[3];
                                break;

                            case (appointmentStartTime >= auxTime3pm &&
                                 appointmentStartTime < auxTime6pm):
                                ++slotStatistic[4];
                                break;

                            case (appointmentStartTime >= auxTime6pm):
                                ++slotStatistic[5];
                                break;
                        }
                    }
                }
            }
        }
        
        for (i = 0; i < 6; i++) {
            slotStatistic[i] = slotStatistic[i] / appointmentTotal;
        }
                
        res.status(200).json(slotStatistic);
    });
};

// GET - Ingreso por día en un mes.
exports.getEntryByDay = function(req, res) {
    console.log('GET /statistics/:medicID/:month/entryByDay');
    
    days.find({ medicID: req.params.medicID }, function(err, days) {
        if ( err )
            return;

        var dayAux;
        var dayStatistic = [];
        var dayDate;
        var monthDate = new Date();
        //var appointmentTotal = 0;
        monthDate.setMonth(req.params.month - 1);
        
        /*for (i = 0; i < days.length; i++) {
            dayAux = days[i];
            dayDate = new Date(dayAux.date);
            if (dayDate.getMonth() == monthDate.getMonth()) {        
                for (k = 0; k < dayAux.dayAppointments.length; k++) {
                    if (dayAux.dayAppointments[k].eventType.localeCompare('Consulta') == 0)
                        ++appointmentTotal;
                }
            }
        }*/
        
        for (i = 0; i < 31; i++) 
            dayStatistic[i] = 0;
            
        for (i = 0; i < days.length; i++) { 
            dayAux = days[i];
            dayDate = new Date(dayAux.date);

            if (dayDate.getMonth() == monthDate.getMonth()) {
                for (k = 0; k < dayAux.dayAppointments.length; k++) {
                    var appointment = dayAux.dayAppointments[k];
                    var auxDate = new Date(dayAux.date);
                    console.log(auxDate.getDate());
                    
                    if (appointment.eventType.localeCompare('Consulta') == 0) {
                        ++dayStatistic[auxDate.getDate() - 1];
                    }
                }
            }
        }
        
        /*for (i = 0; i < 31; i++) {
            dayStatistic[i] = dayStatistic[i] / appointmentTotal;
        }*/
                
        res.status(200).json(dayStatistic);
    });
};