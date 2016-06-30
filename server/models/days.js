/*
* Archivo: models/days.js
* Provee el modelo de la estructura de citas en la BD noSQL, con un objeto de Mongoose y lo exporta.
*/

var mongoose = require('mongoose');

// Contiene la información de las citas. Subdocumento de daySchema.
var appointmentSchema = new mongoose.Schema({
    start: { type: String, trim: true },
    end: { type: String, trim: true },
    eventType: { type: String, trim: true },
    description: { type: String, trim: true },
    patientID: { type: String, trim: true },
    patientName: { type: String, trim: true },
    patientLastName: { type: String, trim: true },
    patientSex: { type: String, trim: true },  // Used for statistics.
    patientAge: { type: Number }               // Used for statistics.
});

// Contiene la información de cada día del calendario.
var daySchema = new mongoose.Schema({
    date: { type: Date },
    medicID: { type: String, trim: true },
    full: Boolean,
    dayAppointments: [appointmentSchema]
});

exports = module.exports = mongoose.model('Day', daySchema);
