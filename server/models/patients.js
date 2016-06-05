/*
* Archivo: models/patients.js
* Provee el modelo de la estructura de los pacientes en la BD noSQL, con un objeto de Mongoose y lo exporta.
*/

var mongoose = require('mongoose');

// Contiene el feedback de un tratamiento para un diagnóstico. Subdocumento de diagnóstico.
var treatmentResultSchema = new mongoose.Schema({
    rating: { type: Number }
});

// Contiene el tratamiento de un diagnóstico. Subdocumento de diagnóstico.
var treatmentSchema = new mongoose.Schema({
    medication: { type: String, trim: true },
    quantity: { type: String, trim: true },
    duration: { type: String, trim: true },
    frequency: { type: String, trim: true }
});

// Contiene un diagnóstico de un paciente. Subdocumento de paciente.
var diagnosticSchema = new mongoose.Schema({
    date: { type: Date },
    diagnostic: { type: String, trim: true },
    treatment: [treatmentSchema],
    treatmentResult: treatmentResultSchema
});

// Contiene la información de los usuarios pacientes, incluyendo su historial médico y feedback de sus tratamientos.
var patientSchema = new mongoose.Schema({
    patientID: { type: String, trim: true },
    name: { type: String, trim: true },
    lastName: { type: String, trim: true },
    birthdate: { type: Date, trim: true },
    email: { type: String, trim: true },
    phoneNumber: { type: String, trim: true },
    sex: { type: String, trim: true },
    weight: { type: Number },
    height: { type: Number },
    medicalBackgrounds: [{ type: String, trim: true }],
    allergies: [{ type: String, trim: true }],
    diagnostics: [diagnosticSchema]
});

exports = module.exports = mongoose.model('Patient', patientSchema);
