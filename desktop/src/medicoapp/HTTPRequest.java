/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicoapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

/**
 * Contiene todas las solicitudes HTTP al servidor. Código basado en la solicitud HTTP de Sunshine para el curso de aplicaciones Android en Udacity.
 * @author Kahnos <josed1305@gmail.com>
 */
public final class HTTPRequest {
    
    // Realiza la solicitud GET/DELETE al parámetro URL y devuelve el resultado JSON como string.
    public static String executeRequest( String URLString, String requestType ){
        String JSONresult = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream;
        StringBuilder buffer;
        BufferedReader reader = null;
        
        try {
            // Conexión a http://<ip del servidor>:1305/api/URL
            URL URL = new URL("http://127.0.0.1:1305/api/" + URLString);

            // Crea el request a la API y abre la conexión.
            urlConnection = (HttpURLConnection) URL.openConnection();
            // Se prepara los headers de la solicitud.
            switch(requestType){
                case "GET":
                    urlConnection.setRequestMethod("GET");
                    break;
                case "DELETE":
                    urlConnection.setRequestMethod("DELETE");
                    break;
            }
            urlConnection.connect();

            // Lee el resultado en un String.
            inputStream = urlConnection.getInputStream();
            buffer = new StringBuilder();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Se añade una /n para poder visualizar mejor el contenido. No afecta el parsing de JSON.
                buffer.append(line).append("\n");
            }

            if (buffer.length() == 0) {
                // Si el buffer está vacío.
                return null;
            }

            JSONresult = buffer.toString();

            // Ejemplo de JSONresult para days = "{ \"date\": \"2016-05-22T00:00:00.000Z\",\"dayAppointments\": [{\"start\": \"2016-05-22T07:00:00.000Z\",\"end\": \"2016-05-22T09:00:00.000Z\",\"eventType\": \"Consulta\",\"patientID\": \"5642196\",\"patientName\": \"Ninfa Araque\",\"description\": \"Best mom ever.\"}],\"full\": true,\"medicID\": \"22824486\"}";
            //System.out.println("Day string inside HTTPRequest: " + JSONresult);
        } catch (Exception e) {
            System.err.println("Error: " + e);
            
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    System.err.println("Error cerrando el stream: " + e);
                }
            }
        }
        
        return JSONresult;
    }
    
    // Realiza la solicitud POST/PUT al parámetro URL y devuelve el resultado JSON como string.
    public static String executeRequest( String URLString, String parameterJSON, String requestType ){
        String resultJSON = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream;
        StringBuilder buffer;
        BufferedReader reader = null;
        DataOutputStream writer = null;
        
        try {
            // Conexión a http://<ip del servidor>:1305/api/URL
            URL URL = new URL("http://127.0.0.1:1305/api/" + URLString);

            // Crea el request a la API y abre la conexión.
            urlConnection = (HttpURLConnection) URL.openConnection();
            
            // Se prepara los headers de la solicitud.
            switch(requestType){
                case "POST":
                    urlConnection.setRequestMethod("POST");
                    break;
                case "PUT":
                    urlConnection.setRequestMethod("PUT");
                    break;
            }
            urlConnection.setRequestProperty("Content-Type", 
        "application/json");
            urlConnection.setRequestProperty("Content-Length", 
        Integer.toString(parameterJSON.getBytes().length));
            urlConnection.setUseCaches(false);
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            // Se envía el JSON con el contenido a introducir o actualizar en la BD.
            writer = new DataOutputStream (
                urlConnection.getOutputStream());
            writer.writeBytes(parameterJSON);
            
            // Lee el resultado en un String.
            inputStream = urlConnection.getInputStream();
            buffer = new StringBuilder();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Se añade una /n para poder visualizar mejor el contenido. No afecta el parsing de JSON.
                buffer.append(line).append("\n");
            }

            if (buffer.length() == 0) {
                // Si el buffer está vacío.
                return null;
            }

            resultJSON = buffer.toString();

            // Ejemplo de JSONresult para days = "{ \"date\": \"2016-05-22T00:00:00.000Z\",\"dayAppointments\": [{\"start\": \"2016-05-22T07:00:00.000Z\",\"end\": \"2016-05-22T09:00:00.000Z\",\"eventType\": \"Consulta\",\"patientID\": \"5642196\",\"patientName\": \"Ninfa Araque\",\"description\": \"Best mom ever.\"}],\"full\": true,\"medicID\": \"22824486\"}";
            //System.out.println("Day string inside HTTPRequest: " + resultJSON);
        } catch (Exception e) {
            System.err.println("Error: " + e);
            
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    System.err.println("Error cerrando el stream de entrada: " + e);
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (final IOException e) {
                    System.err.println("Error cerrando el stream de salida: " + e);
                }
            }
        }
        
        return resultJSON;
    }
    
    // ---------- Solicitudes relacionadas a los días y sus eventos. ----------
    // Obtiene los días de un médico específico. - GET api/days/:medicID
    public static Day[] getDays( String medicID ){  
        Day[] days;
        
        // Se realiza la solicitud GET de los días del médico.
        String DaysString = executeRequest( "days/" + medicID, "GET" );
        
        // Se transforma el resultado en JSON a objeto.
        Gson gson = new GsonBuilder().create();
        days = gson.fromJson(DaysString, Day[].class);
        System.out.println("Object Array to String: " + Arrays.toString(days));
        System.out.println("Object Array to JSON: " + gson.toJson(days));
        
        return days;
    }
    
    // Añade un nuevo día al calendario de un doctor. - POST api/days
    public static Day addDay( Day dayParameter ){ 
        Day day;
        Gson gson = new GsonBuilder().create();
                
        // Se realiza la solicitud POST de un día del médico.
        String DayString = executeRequest( "days", gson.toJson(dayParameter), "POST" );
        
        // Se transforma el resultado en JSON a objeto.
        day = gson.fromJson(DayString, Day.class);
        System.out.println("Object to String: " + day);
        System.out.println("Object to JSON: " + gson.toJson(day));
        
        return day;
    }
    
    // Elimina una cita del día de un doctor. - DELETE api/days/:id/:appointment_id
    public static Day deleteAppointment( String dayID, String appointmentID ){  
        Day day;
        
        // Se realiza la solicitud DELETE de los días del médico.
        String DayString = executeRequest( "days/" + dayID + "/" + appointmentID , "DELETE" );
        
        // Se transforma el resultado en JSON a objeto.
        Gson gson = new GsonBuilder().create();
        day = gson.fromJson(DayString, Day.class);
        System.out.println("Object to String: " + day);
        System.out.println("Object to JSON: " + gson.toJson(day));
        
        return day;
    }
    
    // Actualiza el día de un médico. - PUT api/days/:id
    public static Day updateDay( String dayID, Day dayParameter ){  //
        Day day;
        Gson gson = new GsonBuilder().create();
                
        // Se realiza la solicitud PUT de los días del médico.
        String DayString = executeRequest( "days/" + dayID, gson.toJson(dayParameter), "PUT" );
        
        // Se transforma el resultado en JSON a objeto.
        day = gson.fromJson(DayString, Day.class);
        System.out.println("Object to String: " + day);
        System.out.println("Object to JSON: " + gson.toJson(day));
        
        return day;
    }
    
    // Añade un evento al día de un médico. - POST api/days/:id
    public static Day addAppointment( String dayID, Appointment appointmentParameter ){ 
        Day day;
        Gson gson = new GsonBuilder().create();
                
        // Se realiza la solicitud POST de los días del médico.
        String dayString = executeRequest( "days/" + dayID, gson.toJson(appointmentParameter), "POST" );
        
        // Se transforma el resultado en JSON a objeto.
        day = gson.fromJson(dayString, Day.class);
        System.out.println("Object to String: " + day);
        System.out.println("Object to JSON: " + gson.toJson(day));
        
        return day;
    }
    
    // ---------- Solicitudes relacionadas a las configuraciones. ----------
    // Obtiene la configuración de un doctor específico. - GET api/configs/:medicID
    public static Config getConfig( String medicID ){  
        Config config;
        
        String configString = executeRequest( "configs/" + medicID, "GET" );
        
        // Se transforma el resultado en JSON a objeto.
        Gson gson = new GsonBuilder().create();
        config = gson.fromJson(configString, Config.class);
        System.out.println("Object to String: " + config);
        System.out.println("Object to JSON: " + gson.toJson(config));
        
        return config;
    }
    
    // Inserta una nueva configuración. - POST api/configs/
    public static Config addConfig( Config configParameter ){ 
        Config config;
        Gson gson = new GsonBuilder().create();
                
        String configString = executeRequest( "configs", gson.toJson(configParameter), "POST" );
        
        // Se transforma el resultado en JSON a objeto.
        config = gson.fromJson(configString, Config.class);
        System.out.println("Object to String: " + config);
        System.out.println("Object to JSON: " + gson.toJson(config));
        
        return config;
    }
    
    // Modifica una configuración existente. - PUT api/configs/:id
    public static Config updateConfig( String configID, Config configParameter ){  //
        Config config;
        Gson gson = new GsonBuilder().create();
                
        String configString = executeRequest( "configs/" + configID, gson.toJson(configParameter), "PUT" );
        
        // Se transforma el resultado en JSON a objeto.
        config = gson.fromJson(configString, Config.class);
        System.out.println("Object to String: " + config);
        System.out.println("Object to JSON: " + gson.toJson(config));
        
        return config;
    }
    
    // ---------- Solicitudes relacionadas a los pacientes. ----------
    // Obtiene la información de todos los pacientes. - GET api/patients/
    public static Patient[] getAllPatients(){  
        Patient[] patients;
        
        String patientString = executeRequest( "patients", "GET" );
        
        // Se transforma el resultado en JSON a objeto.
        Gson gson = new GsonBuilder().create();
        patients = gson.fromJson(patientString, Patient[].class);
        System.out.println("Object Array to String: " + Arrays.toString(patients));
        System.out.println("Object Array to JSON: " + gson.toJson(patients));
        
        return patients;
    }
    
    // Obtiene la información específica de un paciente. - GET api/patients/:id
    public static Patient getPatient( String patientID ){  
        Patient patient;
        
        String patientString = executeRequest( "patients/" + patientID, "GET" );
        
        // Se transforma el resultado en JSON a objeto.
        Gson gson = new GsonBuilder().create();
        patient = gson.fromJson(patientString, Patient.class);
        System.out.println("Object to String: " + patient);
        System.out.println("Object to JSON: " + gson.toJson(patient));
        
        return patient;
    }
    
    // Modifica la información de un paciente. - PUT api/patients/:id
    public static Patient updatePatient( String patientID, Patient patientParameter ){  //
        Patient patient;
        Gson gson = new GsonBuilder().create();
                
        String patientString = executeRequest( "patients/" + patientID, gson.toJson(patientParameter), "PUT" );
        
        // Se transforma el resultado en JSON a objeto.
        patient = gson.fromJson(patientString, Patient.class);
        System.out.println("Object to String: " + patient);
        System.out.println("Object to JSON: " + gson.toJson(patient));
        
        return patient;
    }
    
    // Inserta un nuevo paciente. - POST api/patients/
    public static Patient addPatient( Patient patientParameter ){ 
        Patient patient;
        Gson gson = new GsonBuilder().create();
                
        String patientString = executeRequest( "patients", gson.toJson(patientParameter), "POST" );
        
        // Se transforma el resultado en JSON a objeto.
        patient = gson.fromJson(patientString, Patient.class);
        System.out.println("Object to String: " + patient);
        System.out.println("Object to JSON: " + gson.toJson(patient));
        
        return patient;
    }
    
    // ---------- Solicitudes relacionadas a los diagnósticos. ----------
    // Obtiene la información específica de un diagnóstico. - GET api/patients/:id
    public static Diagnostic getDiagnostic( String patientID, String diagnosticID ){  
        Diagnostic diagnostic;
        
        String diagnosticString = executeRequest( "patients/" + patientID + "/" + diagnosticID, "GET" );
        
        // Se transforma el resultado en JSON a objeto.
        Gson gson = new GsonBuilder().create();
        diagnostic = gson.fromJson(diagnosticString, Diagnostic.class);
        System.out.println("Object to String: " + diagnostic);
        System.out.println("Object to JSON: " + gson.toJson(diagnostic));
        
        return diagnostic;
    }
    
    // Inserta un nuevo diagnóstico y retorna al paciente actualizado. - POST api/patients/:id
    public static Patient addDiagnostic( String patientID, Diagnostic diagnosticParameter ){ 
        Patient patient;
        Gson gson = new GsonBuilder().create();
                
        String patientString = executeRequest( "patients/" + patientID, gson.toJson(diagnosticParameter), "POST" );
        
        // Se transforma el resultado en JSON a objeto.
        patient = gson.fromJson(patientString, Patient.class);
        System.out.println("Object to String: " + patient);
        System.out.println("Object to JSON: " + gson.toJson(patient));
        
        return patient;
    }
    
    // Inserta un nuevo diagnóstico y retorna al paciente actualizado. - POST api/patients/:id/:diagnosticID
    public static Diagnostic addFeedback( String patientID, String diagnosticID, TreatmentResult resultParameter ){ 
        Diagnostic diagnostic;
        Gson gson = new GsonBuilder().create();
                
        String diagnosticString = executeRequest( "patients/" + patientID + "/" + diagnosticID, gson.toJson(resultParameter), "POST" );
        
        // Se transforma el resultado en JSON a objeto.
        diagnostic = gson.fromJson(diagnosticString, Diagnostic.class);
        System.out.println("Object to String: " + diagnostic);
        System.out.println("Object to JSON: " + gson.toJson(diagnostic));
        
        return diagnostic;
    }
    
    // ---------- Solicitudes relacionadas a los usuarios. ----------
    // Obtiene la información específica de un usuario. - GET api/users/:id
    public static User getUser( String userID ){  
        User user;
        
        // Se realiza la solicitud GET del usuario.
        String userString = executeRequest( "users/" + userID, "GET" );
        
        // Se transforma el resultado en JSON a objeto.
        Gson gson = new GsonBuilder().create();
        user = gson.fromJson(userString, User.class);
        System.out.println("Object to String: " + user);
        System.out.println("Object to JSON: " + gson.toJson(user));
        
        return user;
    }
    
    // Modifica la información de un usuario. - PUT api/users/:id
    public static User updatePatient( String userID, User userParameter ){  //
        User user;
        Gson gson = new GsonBuilder().create();
                
        // Se realiza la solicitud PUT del usuario.
        String userString = executeRequest( "users/" + userID, gson.toJson(userParameter), "PUT" );
        
        // Se transforma el resultado en JSON a objeto.
        user = gson.fromJson(userString, User.class);
        System.out.println("Object to String: " + user);
        System.out.println("Object to JSON: " + gson.toJson(user));
        
        return user;
    }
    
    // Inserta un nuevo usuario. - POST api/users/
    public static User addUser( User userParameter ){ 
        User user;
        Gson gson = new GsonBuilder().create();
                
        // Se realiza la solicitud POST del usuario.
        String userString = executeRequest( "users", gson.toJson(userParameter), "POST" );
        
        // Se transforma el resultado en JSON a objeto.
        user = gson.fromJson(userString, User.class);
        System.out.println("Object to String: " + user);
        System.out.println("Object to JSON: " + gson.toJson(user));
        
        return user;
    }
    
    // ---------- Obtener datos especificos. ----------
    // Obtiene los appointments de un paciente especifico. - GET api/days/getSharedAppointments
    public static Appointment[] getAppointmentP( String[] appointmentIDs ){  
        Appointment[] App;
        // Se transforma el resultado en JSON a objeto.
        Gson gson = new GsonBuilder().create();
        
        // Se realiza la solicitud GET de los días del médico.
        Arrays.toString(appointmentIDs);
        String AppointmentsString = executeRequest( "days/getSharedAppointments/algo",
                "{ \"appointmentIDs\": " + gson.toJson(appointmentIDs) + " }", "POST");
     
        App = gson.fromJson(AppointmentsString, Appointment[].class);
        System.out.println("Object Array to String: " + Arrays.toString(App));
        System.out.println("Object Array to JSON: " + gson.toJson(App));
        
        return App;
    }
    
}