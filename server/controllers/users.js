/*
* Archivo: controllers/users.js
* Provee los manejadores de solicitudes HTTP para los usuarios.
*/

var users = require('../models/users.js');

// GET - Retorna un usuario específico.
exports.getUser = function(req, res) {
    console.log('GET /users/:id');
    
    // Se busca el documento por su id, si se encuentra, se retorna.
    users.findById(req.params.id, function(err, user) {
        if ( err )
            return res.status(500).send(err.message);

        res.status(200).jsonp(user);
    });
};

// POST - Inserta un nuevo paciente en la base de datos.
exports.addUser = function(req, res) {
    console.log('POST /users');
    console.log(req.body);
    
    // Se crea un nuevo documento.
    user = new users({
        userID : req.body.userID,
        password : req.body.password,
        userType : req.body.userType,
        name : req.body.name,
		lastName : req.body.lastName,
        birthdate : req.body.birthdate,
        email : req.body.email,
        phoneNumber : req.body.phoneNumber,
        sex : req.body.sex,
        medicID : req.body.medicID
    });

    user.save(function(err, user) {
        if ( err )
            return res.status(500).send(err.message);

        res.status(200).jsonp(user);
    });
};

// PUT - Actualiza un usuario existente en la base de datos.
exports.updateUser = function(req, res) {
    console.log('PUT /users/:id');

    // Se busca el documento por su id, si se encuentra, se modifica.
    users.findById(req.params.id, function(err, user) {
        if ( err )
            return res.status(500).send(err.message);

        user.userID = req.body.userID || user.userID;
        user.password = req.body.password || user.password;
        user.userType = req.body.userType || user.userType;
        user.name = req.body.name || user.name;
		user.lastName = req.body.lastName || user.lastName;
        user.birthdate = req.body.birthdate || user.birthdate;
        user.email = req.body.email || user.email;
        user.phoneNumber = req.body.phoneNumber || user.phoneNumber;
        user.sex = req.body.sex || user.sex;
        user.medicID = req.body.medicID || user.medicID;

        user.save(function(err) {
            if ( err )
                return res.status(500).send(err.message);

            res.status(200).jsonp(user);
        });
    });
};

// POST - Login rudimentario
exports.login = function(req, res) {
    console.log('POST /users/login');
    
    users.find({ userID: req.body.userID, password: req.body.password }, function(err, user) {
        if (err)
            return res.status(500).send(err.message);
        
        res.status(200).jsonp(user);
    });
};
