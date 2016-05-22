/*
* Archivo: routes/days.js
* Provee los manejadores de solicitudes HTTP para las citas.
*/

var days = require('../models/days.js');

//GET - Retorna todos los días en la colección.
exports.findAllDays = function(req, res) {
	days.find(function(err, days) {
        if(err) res.send(500, err.message);

        console.log('GET /days');
        res.status(200).jsonp(days);
	});
};

//GET - Retorna todos los días en la colección de un doctor específico.
exports.findAllDaysByDoctor = function(req, res) {
	days.find(function(err, days) {
        if(err) res.send(500, err.message);

        console.log('GET /days?Doctor');
        res.status(200).jsonp(days);
	}).where({ medicID: req.params.medicID });
};
