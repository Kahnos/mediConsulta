/*
* Archivo: controllers/days.js
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

// DELETE - Se elimina un evento del día de un doctor.
exports.removeAppointment = function(req, res) {
    console.log('DELETE /days/:id/:appointment_id');

    // Se busca el documento por su id, si se encuentra, se elimina el evento con id = appointment_id.
    days.findById(req.params.id, function(err, day) {
        if ( err )
            return res.status(500).send(err.message);

        // Se elimina la cita del arreglo de citas.
        day.dayAppointments.id(req.params.appointment_id).remove();

        day.save(function(err) {
            if ( err )
                return res.status(500).send(err.message);

            res.status(200).jsonp(day);
        });
    });
};

// GET - Se obtienen todos las citas de un paciente específico.
exports.getAllPatientAppointments = function(req, res) {
    console.log('POST /days/getSharedAppointments');
    
    var mongoose = require('mongoose');
    days.find(function(err, daysArray) {
        if ( err )
            return;

        var dayAux;
        var appointmentIDs = req.body.appointmentIDs;
        var appointmentResult = [];
        for (i = 0; i < daysArray.length; i++) { 
            dayAux = daysArray[i];
            for (j = 0; j < dayAux.dayAppointments.length; j++) {
                for (k = 0; k < appointmentIDs.length; k++) {
                    if (dayAux.dayAppointments[j]._id == appointmentIDs[k]) {
                        /*console.log("dayAux" + dayAux.dayAppointments[j]._id);
                        console.log("appIDs" + appointmentIDs[k]);*/
                        appointmentResult.push(dayAux.dayAppointments[j]);
                    }
                } 
            }
        }
        
        res.status(200).json(appointmentResult);
    });
};

























