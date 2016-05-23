/*
* Archivo: routes/days.js
* Provee los manejadores de solicitudes HTTP para las citas.
*/

var days = require('../models/days.js');

// GET - Retorna todos los días en la colección de un doctor específico.
exports.findAllDaysByDoctor = function(req, res) {
    console.log('GET /days/doctorID');
    days.find(function(err, days) {
        if ( err )
            return res.status(500).send(err.message);

        res.status(200).jsonp(days);
    }).where({ medicID: req.params.medicID });
};

// POST - Inserta un nuevo día en la base de datos.
exports.addDay = function(req, res) {
    console.log('POST /days');
    console.log(req.body);

    // Se crea un nuevo documento.
    day = new days({
        medicID : req.body.medicID,
        date : req.body.date,
        full : req.body.full,
        dayAppointments : req.body.dayAppointments
    });

    day.save(function(err, day) {
        if ( err )
            return res.status(500).send(err.message);

        res.status(200).jsonp(day);
    });
};

// PUT - Actualiza un día existente en la base de datos.
exports.updateDay = function(req, res) {
    console.log('PUT /days/:id');

    // Se busca el documento por su id, si se encuentra, se modifica.
    days.findById(req.params.id, function(err, day) {
        if ( err )
            return res.status(500).send(err.message);

        day.medicID = req.body.medicID || day.medicID;
        day.date = req.body.date || day.date;
        day.full = req.body.full || day.full;
        day.dayAppointments = req.body.dayAppointments || day.dayAppointments;

        day.save(function(err) {
            if ( err )
                return res.status(500).send(err.message);

            res.status(200).jsonp(day);
        });
    });
};

// POST - Añade un nuevo evento al día de un doctor.
exports.addAppointment = function(req, res) {
    console.log('POST /days/:id');
    console.log(req.body);

    // Se busca el documento por su id, si se encuentra, se le añade un nuevo evento.
    days.findById(req.params.id, function(err, day) {
        if ( err )
            return res.status(500).send(err.message);

        // Se añade los datos de la solicitud al arreglo de citas.
        day.dayAppointments.addToSet(req.body);

        day.save(function(err) {
            if ( err )
                return res.status(500).send(err.message);

            res.status(200).jsonp(day);
        });
    });
};
