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
};
