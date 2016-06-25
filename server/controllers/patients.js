/*
* Archivo: routes/patients.js
* Provee los manejadores de solicitudes HTTP para los pacientes.
*/

var patients = require('../models/patients.js');

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
    
    // Se busca el documento por su id, si se encuentra, se modifica.
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

// PUT - Actualiza una configuración existente en la base de datos.
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