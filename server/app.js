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
mongoose.connect('mongodb://localhost/mediConecta');
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

    // Rutas de citas.
    var days = express.Router();
    
    // Se asignan a las rutas para las sollicitudes de citas sus respectivos manejadores.
    days.route('/days')
      .get(daysController.findAllDays);
 /*     .post(daysController.addTVShow);

    days.route('/days/:id')
      .get(daysController.findById)
      .put(daysController.updateTVShow)
      .delete(daysController.deleteTVShow);*/

    app.use('/api', days);

    // Se inicia el servidor en el puerto 1305 de localhost.
    app.listen(1305, function() {
      console.log("Servidor Node corriendo en http://localhost:1305");
    });
});