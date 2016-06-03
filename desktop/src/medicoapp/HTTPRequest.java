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
    
    // Obtiene los días de un médico específico.
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
    
    // Añade un nuevo día al calendario de un doctor.
    public static Day addDay( Day dayParameter ){ 
        Day day;
        Gson gson = new GsonBuilder().create();
        
        /*String testJSON = "{\n" +
            "  \"medicID\": \"22824486\",\n" +
            "  \"date\": \"2016-05-31T13:00:00.000Z\",\n" +
            "  \"full\": false,\n" +
            "  \"dayAppointments\": [\n" +
            "    {\n" +
            "      \"start\": \"2016-05-31T13:00:00.000Z\",\n" +
            "      \"end\": \"2016-05-31T15:00:00.000Z\",\n" +
            "      \"eventType\": \"Consulta\",\n" +
            "      \"patientID\": \"5642196\",\n" +
            "      \"patientName\": \"Bast\",\n" +
            "      \"patientLastName\": \"Fata\",\n" +
            "      \"description\": \"Dolor de cabeza, nauseas.\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"start\": \"2016-05-31T15:00:00.000Z\",\n" +
            "      \"end\": \"2016-05-31T17:00:00.000Z\",\n" +
            "      \"eventType\": \"Vacaciones\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";*/
                
        // Se realiza la solicitud GET de los días del médico.
        String DayString = executeRequest( "days", gson.toJson(dayParameter), "POST" );
        
        // Se transforma el resultado en JSON a objeto.
        day = gson.fromJson(DayString, Day.class);
        System.out.println("Object  to String: " + day);
        System.out.println("Object Array to JSON: " + gson.toJson(day));
        
        return day;
    }
    
}