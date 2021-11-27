package org.log4j.appender;

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
     * Se autentica contra el servidor de autenticación y obtiene un jwtToken
     *
     * @return
     */
    public String authenticate() {
        try {
            HttpURLConnection connection = createPostConnection(authenticateUrl, "application/json; utf-8");
            jwtToken = sendPostData(connection, "{ \"agentToken\" : \"" + agentTokenId + "\" }\n");

            connection.disconnect();
        } catch (IOException io) {
            jwtToken = null;
            io.printStackTrace();
        }
        return jwtToken;
    }

    public String putLogAndRetry(String log) {
        String result = putLog(log);
        if (result == null || !result.equals("OK")) {
            authenticate();
            result = putLog(log);
        }
        return result;
    }

    /**
     * Crea una conexión al servidor para poder realizar un post
     *
     * @return
     * @throws IOException
     */
    private HttpURLConnection createPostConnection(String urlString, String contentType) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", contentType);
        if (jwtToken != null) {
            connection.setRequestProperty("Authorization", jwtToken);
        }
        connection.setDoOutput(true);

        return connection;
    }

    private String putLog(String log) {
        try {
            HttpURLConnection connection = createPostConnection(putUrl, "text/plain; utf-8");
            String result = sendPostData(connection, log);
            connection.disconnect();
            return result;

        } catch (IOException io) {
            io.printStackTrace();
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
    private String sendPostData(HttpURLConnection connection, String json) throws IOException {
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = json.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            return response.toString();
        }
    }

}
