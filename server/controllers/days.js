/*
* Archivo: routes/days.js
* Provee los manejadores de solicitudes HTTP para las citas.
*/

var days = require('../models/days.js');

// GET - Retorna todos los días en la colección.
/*exports.findAllDays = function(req, res) {
	days.find(function(err, days) {
        if(err) res.send(500, err.message);

        console.log('GET /days');
        res.status(200).jsonp(days);
	});
};*/

// GET - Retorna todos los días en la colección de un doctor específico.
exports.findAllDaysByDoctor = function(req, res) {
	days.find(function(err, days) {
        if(err) res.send(500, err.message);

        console.log('GET /days/doctorID');
        res.status(200).jsonp(days);
	}).where({ medicID: req.params.medicID });
};

// POST - Inserta un nuevo día en la base de datos.
exports.addDay = function(req, res) {
    console.log('POST /days/:id');
	console.log(req.body);
    
    // Se crea un nuevo documento.
    day = new days({
        medicID : req.body.medicID,
        date : req.body.date,
        full : req.body.full,
        dayAppointments : req.body.dayAppointments
    });
    
    //day.dayAppointments = JSON.parse(JSON.stringify(req.body.dayAppointments));
    
    /*day = new days({
       JSON.parse(JSON.stringify(req.body))
    });*/
    
    day.save(function(err, day) {
		if(err) return res.status(500).send(err.message);
        
        res.status(200).jsonp(day);
	});
}

// POST - Inserta un nuevo día en la base de datos o modifica uno si ya existe.
/*exports.createOrUpdateDay = function(req, res) {
	console.log('POST /days/medicID/date');
	console.log(req.body);

    var query = { medicID: req.params.medicID, date: req.params.date },
        update = {
            // Se modifican todos los campos.
            full : req.body.full,
            dayAppointments : req.body.dayAppointments.slice()
        },
        options = { upsert: true }; // Este parámetro permite crear un documento nuevo si no se encuentra.

    // Se busca el documento.
    Model.findOneAndUpdate(query, update, options, function(error, result) {
        if (!error) {
            // Si el documento no existe.
            if (!result) {
                // Se crea un nuevo documento.
                day = new days({
                    medicID : req.params.medicID,
                    date : req.params.date,
                    full : req.body.full,
                    dayAppointments : req.body.dayAppointments.slice()
                });
            }
            // Se guarda el documento.
            result.save(function(error) {
                if (!error) {
                    return res.status(200).jsonp(day);
                } else {
                    return res.send(500, err.message);
                }
            });
        }
    });
};*/