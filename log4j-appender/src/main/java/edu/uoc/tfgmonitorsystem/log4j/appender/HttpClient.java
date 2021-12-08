package edu.uoc.tfgmonitorsystem.log4j.appender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

    private final String authenticateUrl;
    private final String putUrl;
    private final String agentTokenId;
    private String jwtToken;

    public HttpClient(String authenticateUrl, String putUrl, String agentTokenId) {
        this.authenticateUrl = authenticateUrl;
        this.putUrl = putUrl;
        this.agentTokenId = agentTokenId;
    }

    /**
     * Entrega una línea de log y revisa que está autenticado.
     *
     * @param log String con el log.
     * @return
     */
    public boolean putLogAndRetry(String log) {

        if (jwtToken == null) {
            authenticate();
        }
        if (jwtToken != null) {
            String result = putLog(log);
            return result != null && result.equals("OK");
        }
        return false;
    }

    /**
     * Se autentica contra el servidor de autenticación y obtiene un jwtToken
     *
     * @return String con el token autenticado o null si no ha autenticado correctamente.
     */
    private void authenticate() {
        HttpURLConnection connection = createPostConnection(authenticateUrl, "application/json; utf-8");
        if (connection != null) {
            jwtToken = sendPostData(connection, "{ \"agentToken\" : \"" + agentTokenId + "\" }\n");
            connection.disconnect();

            System.out.println("authenticate. " + jwtToken);
        } else {
            jwtToken = null;
        }
    }

    /**
     * Crea una conexión al servidor para poder realizar un post
     *
     * @return
     * @throws IOException
     */
    private HttpURLConnection createPostConnection(String urlString, String contentType) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", contentType);
            if (jwtToken != null) {
                connection.setRequestProperty("Authorization", jwtToken);
            }
            connection.setDoOutput(true);
            return connection;
        } catch (IOException e) {
            System.out.println("createPostConnection. Cannot create connection " + authenticateUrl);
        }
        return null;
    }

    /**
     * Escribe una línea de log en la conexión y espera el resultado OK.
     *
     * @param log
     * @return
     */
    private String putLog(String log) {
        HttpURLConnection connection = createPostConnection(putUrl, "text/plain; utf-8");

        if (connection != null) {
            String result = sendPostData(connection, log);
            connection.disconnect();
            return result;

        }
        return null;
    }

    /**
     * Envía información por post a u na conexión
     *
     * @param connection
     * @param json
     * @return
     * @throws IOException
     */
    private String sendPostData(HttpURLConnection connection, String json) {
        try {
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
        } catch (IOException e) {
            System.out.println("sendPostData. Cannot send " + authenticateUrl + " > " + json);
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            return response.toString();
        } catch (IOException e) {
            System.out.println("sendPostData. Cannot send receive response " + authenticateUrl + " > " + json);
        }
        return null;
    }

}
