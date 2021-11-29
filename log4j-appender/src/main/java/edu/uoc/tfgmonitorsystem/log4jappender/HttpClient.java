package edu.uoc.tfgmonitorsystem.log4jappender;

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
        HttpURLConnection connection = createPostConnection(authenticateUrl, "application/json; utf-8");
        if (connection != null) {
            jwtToken = sendPostData(connection, "{ \"agentToken\" : \"" + agentTokenId + "\" }\n");
            connection.disconnect();
        } else {
            jwtToken = null;
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
            e.printStackTrace();
        }
        return null;
    }

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
//            System.out.println("Cannot send " + json);
//            e.printStackTrace();

        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            return response.toString();
        } catch (IOException e) {
//            System.out.println("Cannot send receive response " + json);
//            e.printStackTrace();
        }
        return null;
    }

}
