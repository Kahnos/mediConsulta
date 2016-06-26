/*
* Archivo: controllers/configs.js
* Provee los manejadores de solicitudes HTTP para la configuración.
*/

var configs = require('../models/configs.js');

// GET - Retorna la configuración de un doctor específico.
exports.getConfig = function(req, res) {
    console.log('GET /configs/medicID');
    configs.find(function(err, config) {
        if ( err )
            return res.status(500).send(err.message);

        res.status(200).jsonp(config);
    }).where({ medicID: req.params.medicID });
};

// POST - Inserta una nueva configuración en la base de datos.
exports.addConfig = function(req, res) {
    console.log('POST /configs');
    console.log(req.body);

    // Se crea un nuevo documento.
    config = new configs({
        medicID : req.body.medicID,
        dayConfigs : req.body.dayConfigs
    });

    config.save(function(err, config) {
        if ( err )
            return res.status(500).send(err.message);

        res.status(200).jsonp(config);
    });
};

// PUT - Actualiza una configuración existente en la base de datos.
exports.updateConfig = function(req, res) {
    console.log('PUT /configs/:id');

    // Se busca el documento por su id, si se encuentra, se modifica.
    configs.findById(req.params.id, function(err, config) {
        if ( err )
            return res.status(500).send(err.message);

        config.dayConfigs = req.body.dayConfigs || config.dayConfigs;

        config.save(function(err) {
            if ( err )
                return res.status(500).send(err.message);

            res.status(200).jsonp(config);
        });
    });
};