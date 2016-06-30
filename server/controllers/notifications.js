/*
* Archivo: notifications.js
* Notifications file that includes all code related to emails and SMS creation and parsing. 
*/

var nodemailer = require('nodemailer');

// Se crea la configuración SMTP para email
var smtpConfig = {
    host: 'smtp.gmail.com',
    port: 465,
    secure: true, // use SSL
    auth: {
        user: 'mediconsulta.desarrollo@gmail.com',
        pass: 'mediconsulta_desarrollo'
    },
    tls: {
        secureProtocol: "TLSv1_method",
        rejectUnauthorized: false
    }
};

// Se crea el objeto de transporte que enviará el email
var transporter = nodemailer.createTransport(smtpConfig);

// Se crean los datos del email
var mailOptions = {
    from: '"mediConsulta" <mediconsulta.desarrollo@gmail.com>', // sender address
    to: 'josed1305@gmail.com', // list of receivers
    subject: '[mediConsulta] Prueba de notificación.', // Subject line
    text: 'Fuck yeah.', // plaintext body
    html: '<b>FUCK YEAH.</b>' // html body
};

// Función de prueba de email
exports.testEmail = function() {
    // Se envía el email
    transporter.sendMail(mailOptions, function(error, info){
        if(error){
            return console.log(error);
        }
        console.log('Message sent: ' + info.response);
    });
}

/*var TMClient = require('textmagic-rest-client');
  
var c = new TMClient('josediaz', 'HvTUZkT6gzUNjrgpKppKEnrxBWtV3Q');
exports.testSMS = function() {
    c.Messages.send({text: 'Fuck yes.', phones:'+584249090336'}, function(err, res){
        console.log('Messages.send()', err, res);
        if (err)
            res.status(500).message("Error delivering sms.");
        
        res.status(200).message("Message delivered.");
    });
}*/

