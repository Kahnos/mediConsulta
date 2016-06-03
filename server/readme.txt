Servidor basado en node.js con base de datos noSQL (MongoDB) y API REST.

Para poder utilizar este servidor, se debe instalar node.js primero y MongoDB. Puede descargarlos desde sus páginas oficiales para su S.O. correspondiente.

Luego de poseer node.js y poder utilizarlo en su línea de comando, utilice los siguientes instrucciones:

npm install
node app.js

"npm install" instalará todas las dependencias requeridas, mientras que "node app.js" iniciará el servidor en el puerto 1305 de localhost (http://localhost:1305). 

Para poder utilizar el servidor, se deberá tener mongod (servidor de MongoDB) corriendo en el sistema.

URLs:

- Días y citas:

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
    "patientLastName": "Sinsangre",
    "description": "Arcanist.", Nota: Descripción es el motivo de la cita médica, no de la persona. 
    "_id": ObjectId("574290705811965c156210dd")
}

DELETE api/days/:id/:appointment_id
Elimina un evento existente con id = appointment_id en el día con id = :id.

GET api/days/:medicID
Obtiene todos los días del médico con medicID = :medicID. Nota: medicID se pensó como la C.I. del médico.

- Configuración:

GET api/configs/:medicID
Obtiene la configuración para un médico específico con medicID = :medicID.

POST api/configs/
Inserta una nueva configuración.

PUT api/configs/:id
Modifica una configuración existente con id = :id.

- Pacientes:

GET api/patients/:id
Obtiene la información específica de un paciente con id = :id.

PUT api/configs/:id
Modifica la información de un paciente con id = :id.

POST api/patients/
Inserta un nuevo paciente.

NOTA: Los ejemplos de los JSONs se pueden encontrar en la carpeta test del proyecto.