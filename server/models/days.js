/*
* Archivo: models/days.js
* Provee el modelo de la estructura de citas en la BD noSQL, con un objeto de Mongoose y lo exporta.
*/

var mongoose = require('mongoose');

// Contiene la información de cada día del calendario. Acá estará la información de las citas.
var daySchema = new mongoose.Schema({
    date: { type: Date },
    medicID: { type: String, trim: true },
    full: Boolean,
    dayAppointments: [{
        timeSlot: {
            start: { type: Date },
            end: { type: Date }
        },
        eventType: { type: String, enum: ['Consulta', 'Vacaciones', 'Reunión', 'Seminario', 'Personal'] }, 
        patient: { // Si tipoEvento no es cita médica, patient estará vacío.   
            patientID: { type: Number, min: 0, max: 99999999 },
            patientName: { type: String, trim: true },
            description: { type: String, trim: true }
        }
   }]
});

exports = module.exports = mongoose.model('Day', daySchema);