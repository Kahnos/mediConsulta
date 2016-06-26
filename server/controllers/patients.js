/*
* Archivo: controllers/patients.js
* Provee los manejadores de solicitudes HTTP para los pacientes.
*/

var patients = require('../models/patients.js');

// Controladores relacionados a un paciente.
// GET - Obtiene todos los pacientes en la base de datos.
exports.getAllPatients = function(req, res) {
    console.log('GET /patients/');
    patients.find(function(err, patients) {
        if ( err )
            return res.status(500).send(err.message);

        res.status(200).jsonp(patients);
    });
}

// GET - Retorna un paciente específico.
exports.getPatient = function(req, res) {
    console.log('GET /patients/:id');
    
    // Se busca el documento por su id, si se encuentra, se retorna.
    patients.findById(req.params.id, function(err, patient) {
        if ( err )
            return res.status(500).send(err.message);

        res.status(200).jsonp(patient);
    });
};

// POST - Inserta un nuevo paciente en la base de datos.
exports.addPatient = function(req, res) {
    console.log('POST /patients');
    console.log(req.body);
    
    // Se crea un nuevo documento.
    patient = new patients({
        patientID : req.body.patientID,
        name : req.body.name,
		lastName : req.body.lastName,
        birthdate : req.body.birthdate,
        email : req.body.email,
        phoneNumber : req.body.phoneNumber,
        sex : req.body.sex,
        weight : req.body.weight,
        height : req.body.height,
        medicalBackgrounds : req.body.medicalBackgrounds,
        allergies : req.body.allergies,
        diagnostics : req.body.diagnostics
    });

    patient.save(function(err, patient) {
        if ( err )
            return res.status(500).send(err.message);

        res.status(200).jsonp(patient);
    });
};

// PUT - Actualiza un paciente existente en la base de datos.
exports.updatePatient = function(req, res) {
    console.log('PUT /patients/:id');

    // Se busca el documento por su id, si se encuentra, se modifica.
    patients.findById(req.params.id, function(err, patient) {
        if ( err )
            return res.status(500).send(err.message);

        patient.patientID = req.body.patientID || patient.patientID;
        patient.name = req.body.name || patient.name;
		patient.lastName = req.body.lastName || patient.lastName;
        patient.birthdate = req.body.birthdate || patient.birthdate;
        patient.email = req.body.email || patient.email;
        patient.phoneNumber = req.body.phoneNumber || patient.phoneNumber;
        patient.sex = req.body.sex || patient.sex;
        patient.weight = req.body.weight || patient.weight;
        patient.height = req.body.height || patient.height;
        patient.medicalBackgrounds = req.body.medicalBackgrounds || patient.medicalBackgrounds;
        patient.allergies = req.body.allergies || patient.allergies;
        patient.diagnostics = req.body.diagnostics || patient.diagnostics;

        patient.save(function(err) {
            if ( err )
                return res.status(500).send(err.message);

            res.status(200).jsonp(patient);
        });
    });
};

// DELETE - Se elimina un paciente específico y todas las citas de ese paciente.
exports.removePatient = function(req, res) {
    console.log('DELETE /patients/:id');

    // Se busca el documento por su id, si se encuentra, se elimina el paciente con id = :id.
    patients.findByIdAndRemove(req.params.id, function(err, patient) {
        if ( err )
            return res.status(500).send(err.message);

        var days = require('../models/days.js');
        
        days.find(function(err, daysArray) {
            if ( err )
                return;

            var dayAux;
            var appointments;
            var appointmentsAux;
            for (i = 0; i < daysArray.length; i++) { 
                dayAux = daysArray[i];
                appointments = [];
                appointmentsAux = false;
                for (j = 0; j < dayAux.dayAppointments.length; j++) {
                    if (dayAux.dayAppointments[j].patientID == patient.patientID){
                        appointments.push(dayAux.dayAppointments[j]);
                        appointmentsAux = true;
                    }
                }
                if (appointmentsAux)
                    (function (appointments) {
                        days.findOneAndUpdate (dayAux._id, function(err, day) {
                            console.log("BEFORE");
                            console.log(day);
                            console.log("INSIDE APPOINTMENTS");
                            console.log(appointments);
                            for (k = 0; k < appointments.length; k++) {
                                day.dayAppointments.id(appointments[k]._id).remove();
                            }
                            console.log("AFTER");
                            console.log(day);
                            day.save();
                        });
                    }(appointments));
            }            
        });
        
        return res.status(200).send("Patient deleted.");
    });
};

// Controladores relacionados al diagnóstico de un paciente.
// GET - Obtiene un diagnóstico específico.
exports.getDiagnostic = function(req, res) {
    console.log('GET /patients/:id/:diagnosticID');
    
    // Se busca el documento por su id, si se encuentra, se modifica.
    patients.findById(req.params.id, function(err, patient) {
        if ( err )
            return res.status(500).send(err.message);

        var diagnostic = null;
        diagnostic = patient.diagnostics.id(req.params.diagnosticID);
        
        if ( diagnostic != null )
            return res.status(200).jsonp(diagnostic);
        else {
            return res.status(500).send("Diagnostic with id " + req.params.diagnosticID + " does not exist.");
        }
    });
}

// POST - Añade un nuevo diagnóstico a un paciente.
exports.addDiagnostic = function(req, res) {
    console.log('POST /patients/:id');
    console.log(req.body);

    // Se busca el documento por su id, si se encuentra, se le añade un nuevo diagnóstico.
    patients.findById(req.params.id, function(err, patient) {
        if ( err )
            return res.status(500).send(err.message);

        // Se añade los datos de la solicitud al arreglo de diagnósticos.
        patient.diagnostics.addToSet(req.body);

        patient.save(function(err) {
            if ( err )
                return res.status(500).send(err.message);

            res.status(200).jsonp(patient);
        });
    });
}

// POST - Añade el feedback de un paciente.
exports.addTreatmentFeedback = function(req, res) {
    console.log('POST /patients/:id/:diagnosticID');
    console.log(req.body);

    // Se busca el documento por su id, si se encuentra, se le añade un nuevo feedback.
    patients.findById(req.params.id, function(err, patient) {
        if ( err )
            return res.status(500).send(err.message);

        var diagnostic = null;
        diagnostic = patient.diagnostics.id(req.params.diagnosticID);
        
        if ( diagnostic != null ) {
            diagnostic.treatmentResult = req.body;
            
            patient.save(function(err) {
                if ( err )
                    return res.status(500).send(err.message);

                res.status(200).jsonp(diagnostic);
            });            
        }
        else {
            return res.status(500).send("Diagnostic with id " + req.params.diagnosticID + " does not exist.");
        }
    });
}