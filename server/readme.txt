Servidor basado en node.js con base de datos noSQL (MongoDB) y API REST.

Para poder utilizar este servidor, se debe instalar node.js primero. Puede descargarlo desde su página oficial para su S.O. correspondiente.

Luego de poseer node.js y poder utilizarlo en su línea de comando, utilice los siguientes instrucciones:

npm install
node app.js

"npm install" instalará todas las dependencias requeridas, mientras que "node app.js" iniciará el servidor en el puerto 1305 de localhost (http://localhost:1305). 

URLs:

POST api/days
Añade un nuevo día. 

PUT api/days/:id
Actualiza un día existente con id = :id.

POST api/days/:id
Añade un nuevo evento en el día con id = :id. Se debe pasar un objeto dayAppointment como: 

{
    "start": ISODate("2016-05-22T13:00:00.000Z"),
    "end": ISODate("2016-05-22T15:00:00.000Z"),
    "eventType": "Consulta",
    "patientID": "4588796",
    "patientName": "Kvothe",
    "description": "Arcanist.", Nota: Descripción es el motivo de la cita médica, no de la persona. 
    "_id": ObjectId("574290705811965c156210dd")
}

DELETE api/days/:id/:appointment_id
Elimina un evento existente con id = appointment_id en el día con id = :id.

GET api/days/:medicID
Obtiene todos los días del médico con medicID = :medicID. Nota: medicID se pensó como la C.I. del médico.

Ejemplo del JSON:

{
    "_id": ObjectId("57427a3d3485541421cf66bd"),
    "__v": 7,
    "date": ISODate("2016-05-22T00:00:00.000Z"),
    "dayAppointments": [
        {
            "start": ISODate("2016-05-22T07:00:00.000Z"),
            "end": ISODate("2016-05-22T09:00:00.000Z"),
            "eventType": "Consulta",
            "patientID": "5642196",
            "patientName": "Ninfa Araque",
            "description": "Best mom ever.",
            "_id": ObjectId("57428a5fca57a1e00d3d782b")
        },
        {
            "start": ISODate("2016-05-22T13:00:00.000Z"),
            "end": ISODate("2016-05-22T15:00:00.000Z"),
            "eventType": "Consulta",
            "patientID": "4588796",
            "patientName": "Kvothe",
            "description": "Arcanist.",
            "_id": ObjectId("574290705811965c156210dd")
        },
        {
            "start": ISODate("2016-05-22T17:00:00.000Z"),
            "end": ISODate("2016-05-22T18:00:00.000Z"),
            "eventType": "Consulta",
            "patientID": "455221",
            "patientName": "Bast",
            "description": "Fata.",
            "_id": ObjectId("574290905811965c156210de")
        }
    ],
    "full": true,
    "medicID": "22824486"
}