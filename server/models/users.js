/*
* Archivo: models/users.js
* Provee el modelo de la estructura de los usuarios en la BD noSQL, con un objeto de Mongoose y lo exporta.
*/

var mongoose = require('mongoose');

// Contiene la información de los médicos que utilizan el sistema.
var userSchema = new mongoose.Schema({
    userID: { type: String, trim: true },
    password: { type: String, trim: true },
    userType: { type: String, trim: true },
    name: { type: String, trim: true },
    lastName: { type: String, trim: true },
    birthdate: { type: Date },
    email: { type: String, trim: true },
    phoneNumber: { type: String, trim: true },
    sex: { type: String, trim: true },
    medicID: { type: String, trim: true }
});

exports = module.exports = mongoose.model('User', userSchema);