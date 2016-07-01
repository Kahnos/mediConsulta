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
                appointmentTotal += dayAux.dayAppointments.length;
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
        
        for (i = 0; i < 10 ; i++) {
            ageStatistic[i] = ageStatistic[i] / appointmentTotal;
        }
                
        res.status(200).json(ageStatistic);
    });
};

// GET - Retorna el promedio de ingreso por sexo - Mensual.
exports.getEntryByAge = function(req, res) {
    console.log('GET /statistics/:medicID/:month/entryBySex');
    
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
                appointmentTotal += dayAux.dayAppointments.length;
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
        
        for (i = 0; i < 10 ; i++) {
            ageStatistic[i] = ageStatistic[i] / appointmentTotal;
        }
                
        res.status(200).json(ageStatistic);
    });
};