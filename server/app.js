/*
* Archivo: app.js
* Archivo principal, se conecta a la BD, inicializa el servidor y crea el middleware que utilizará express. También asignará los manejadores de
* solicitudes a los URL correctos.
*/

var bodyParser  = require("body-parser"), // Permite parsear JSON. Decodificará a JSON la entrada de las solicitudes.
    mongoose = require('mongoose'), // Módulo para manejar bases de dato MongoDB.
    express = require("express"), // Framework de nodeJS.
    app = express();

// Conexión a la BD
mongoose.connect('mongodb://127.0.0.1/mediConsulta');
var db = mongoose.connection;

// Se llama si la conexión con la BD devuelve un error.
db.on('error', console.error.bind(console, 'connection error:'));

// Se llama una vez que la conexión con la BD se realice correctamente.
db.once('open', function() {
    // Se aplica el middleware (bodyparser) a la instancia de express llamada app.
    app.use(bodyParser.urlencoded({ extended: false }));
    app.use(bodyParser.json());

    // Se importan los modelos y los controladores.
    var daysController = require('./controllers/days.js');
    var configsController = require('./controllers/configs.js');
    var patientsController = require('./controllers/patients.js');

    // Router de días y citas.
    var days = express.Router();

    // Router de configuraciones.
    var configs = express.Router();
    
    // Router de pacientes.
    var patients = express.Router();
    
    // Se asignan a las rutas para las solicitudes sus respectivos manejadores.
    // Rutas de días y citas.
    days.route('/days')
        .post(daysController.addDay);

    days.route('/days/:id')
        .put(daysController.updateDay)
        .post(daysController.addAppointment);

    days.route('/days/:id/:appointment_id')
        .delete(daysController.removeAppointment);

    days.route('/days/:medicID')
        .get(daysController.findAllDaysByDoctor);
    
    // Rutas de configuraciones.
    configs.route('/configs/:medicID')
        .get(configsController.getConfig);   
    
    configs.route('/configs/')
        .post(configsController.addConfig);
    
    configs.route('/configs/:id')
        .put(configsController.updateConfig);
    
    // Rutas de pacientes.
    patients.route('/patients/:id')
        .get(patientsController.getPatient)
        .put(patientsController.updatePatient);
    
    patients.route('/patients/')
        .get(patientsController.getAllPatients)
        .post(patientsController.addPatient);
    
    // Se aplican los routers a la API.
    app.use('/api', days);
    app.use('/api', configs);
    app.use('/api', patients);

    // Se inicia el servidor en el puerto 1305 de localhost.
    app.listen(1305, function() {
      console.log("Servidor node.js corriendo en http://localhost:1305");
    });
});
