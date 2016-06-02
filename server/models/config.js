/*
* Archivo: models/config.js
* Provee el modelo de la estructura de la configuración en la BD noSQL, con un objeto de Mongoose y lo exporta.
*/

var mongoose = require('mongoose');

// Contiene la información de las citas. Subdocumento de daySchema.
var dayConfigSchema = new mongoose.Schema({
    day: { type: String, enum: ['Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado', 'Domingo'] },
    scheduleStart: { type: String, trim: true },
    scheduleEnd: { type: String, trim: true },
    slotLength: { type: String, trim: true }
});

// Contiene la información de cada día del calendario.
var configSchema = new mongoose.Schema({
    medicID: { type: String, trim: true },
    dayConfigs: [dayConfigSchema]
});

exports = module.exports = mongoose.model('Day', daySchema);
