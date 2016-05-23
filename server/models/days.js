/*
* Archivo: models/days.js
* Provee el modelo de la estructura de citas en la BD noSQL, con un objeto de Mongoose y lo exporta.
*/

var mongoose = require('mongoose');

// Contiene la información de las citas. Subdocumento de daySchema.
var appointmentSchema = new mongoose.Schema({
    start: { type: Date },
    end: { type: Date },
    eventType: { type: String, enum: ['Consulta', 'Vacaciones', 'Reunión', 'Seminario', 'Personal'] },
    patientID: { type: String, trim: true },
    patientName: { type: String, trim: true },
    description: { type: String, trim: true }
});

// Contiene la información de cada día del calendario.
var daySchema = new mongoose.Schema({
    date: { type: Date },
    medicID: { type: String, trim: true },
    full: Boolean,
    dayAppointments: [appointmentSchema]
});

exports = module.exports = mongoose.model('Day', daySchema);
