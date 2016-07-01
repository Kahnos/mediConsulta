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

    // Se importan los controladores.
    var daysController = require('./controllers/days.js');
    var configsController = require('./controllers/configs.js');
    var patientsController = require('./controllers/patients.js');
    var usersController = require('./controllers/users.js');
    var notificationsController = require('./controllers/notifications.js');
    var formsController = require('./controllers/forms.js');
    var statisticsController = require('./controllers/statistics.js');

    // Router de días y citas.
    var days = express.Router();

    // Router de configuraciones.
    var configs = express.Router();
    
    // Router de pacientes.
    var patients = express.Router();
        
    // Router de usuarios.
    var users = express.Router();
    
    // Router de notificaciones
    var notifications = express.Router();
    
    // Router de formularios
    var forms = express.Router();
    
    // Router de estadísticas
    var statistics = express.Router();
    
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
    
    days.route('/days/getSharedAppointments/:anything')
        .post(daysController.getAllPatientAppointments);
    
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
        .put(patientsController.updatePatient)
        .post(patientsController.addDiagnostic)
        .delete(patientsController.removePatient);
    
    patients.route('/patients/')
        .get(patientsController.getAllPatients)
        .post(patientsController.addPatient);
    
    patients.route('/patients/:id/:diagnosticID')
        .get(patientsController.getDiagnostic)
        .post(patientsController.addTreatmentFeedback);
    
    // Rutas de usuarios.
    users.route('/users')
        .post(usersController.addUser);
    
    users.route('/users/:id')
        .get(usersController.getUser)
        .put(usersController.updateUser);
    
    // Rutas de notificaciones
    /*notifications.route('/sms/test')
        .get(notificationsController.testSMS);*/
    
    notifications.route('/emails/test')
        .get(notificationsController.testEmail);
    
    notifications.route('/scheduler/test')
        .get(notificationsController.testScheduler);
    
    // Rutas de formularios
    forms.route('/forms/test')
        .get(formsController.testForm);
    
    // Rutas de estadísticas
    statistics.route('/statistics/:medicID/:month/entryByAge')
        .get(statisticsController.getEntryByAge);
    
    // Rutas de estadísticas
    statistics.route('/statistics/:medicID/:month/entryBySex')
        .get(statisticsController.getEntryBySex);
    
    // Se aplican los routers a la API.
    app.use('/api', days);
    app.use('/api', configs);
    app.use('/api', patients);
    app.use('/api', users);
    app.use('/api', notifications);
    app.use('/api', forms);
    app.use('/api', statistics);

    // Se inicia el servidor en el puerto 1305 de localhost.
    app.listen(1305, function() {
      console.log("Servidor node.js corriendo en http://localhost:1305");
    });
});
