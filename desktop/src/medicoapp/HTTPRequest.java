/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicoapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

/**
 * Contiene todas las solicitudes HTTP al servidor. Código basado en la solicitud HTTP de Sunshine para el curso de Android en Udacity.
 * @author Kahnos <josed1305@gmail.com>
 */
public final class HTTPRequest {
    
    // Realiza la solicitud GET al parámetro URL y devuelve el resultado JSON como string.
    public static String GETRequest( String URLString ){
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
            urlConnection.setRequestMethod("GET");
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
            System.out.println("Day string: " + JSONresult);
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
    
    public static Day[] getDays( String medicID ){  
        Day[] days;
        
        // Se realiza la solicitud GET de los días del médico.
        String DaysString = GETRequest( "days/" + medicID );
        
        // Se transforma el resultado en JSON a objeto.
        Gson gson = new GsonBuilder().create();
        days = gson.fromJson(DaysString, Day[].class);
        System.out.println(Arrays.toString(days));
        System.out.println(gson.toJson(days));
        
        return days;
    }
    
}