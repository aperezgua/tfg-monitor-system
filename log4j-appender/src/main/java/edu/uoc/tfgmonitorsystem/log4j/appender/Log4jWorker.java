package edu.uoc.tfgmonitorsystem.log4j.appender;

import java.util.Date;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Log4jWorker {

    /**
     * Thread principal encargado de consumir la cola.
     */
    private class Log4jWorkerThread extends Thread {

        @Override
        public void run() {
            System.out.println("Start current thread");

            while (!logToSend.isEmpty()) {
                boolean send = false;
                AgentLineLog agentLineLog = null;
                try {
                    // elimina de la cola.
                    agentLineLog = logToSend.poll();

                    HttpClient client = findHttpClient(agentLineLog);

                    if (client.putLogAndRetry(agentLineLog.getLogLine())) {
                        send = true;
                        System.out.println(new Date() + " > Line send correctly: " + agentLineLog);
                    } else {
                        System.out.println("Error in send: " + agentLineLog);
                        Thread.sleep(1000L);
                    }

                } catch (InterruptedException e) {
                    System.out.println("Log4jWorker interrupt");
                    Thread.currentThread().interrupt();
                } finally {
                    if (!send && agentLineLog != null) {
                        putLogToSend(agentLineLog);
                    }
                }

            }

            System.out.println("Stop current thread");
        }

    }

    private static Log4jWorker instance;

    private Queue<AgentLineLog> logToSend = new ConcurrentLinkedQueue<>();

    private Map<String, HttpClient> clients = new ConcurrentHashMap<>();;

    private Thread currentThread = null;

    private Log4jWorker() {
        super();
    }

    /**
     * Devuelve una instancia del worker de envío de log usando patron singleton.
     *
     * @return Log4jWorker con la instancia única para toda la aplicación.
     */
    public static synchronized Log4jWorker getInstance() {
        if (instance == null) {
            instance = new Log4jWorker();
        }
        return instance;
    }

    /**
     * Agrega una línea a la cola para enviar por el worker.
     *
     * @param authenticationUrl String con la url de autenticación.
     * @param putLogUrl         URL de envío de log.
     * @param agentTokenId      String con el ID del token.
     * @param logLine           String con la línea a enviar.
     */
    public void addLogToSend(String authenticationUrl, String putLogUrl, String agentTokenId, String logLine) {
        putLogToSend(new AgentLineLog(authenticationUrl, putLogUrl, agentTokenId, logLine));
    }

    private void checkThread() {
        if (currentThread == null || currentThread.isInterrupted() || !currentThread.isAlive()) {
            System.out.println("\n#######\nWorker not run, start it");
            currentThread = new Log4jWorkerThread();
            currentThread.start();
        }
    }

    /**
     * Busca un cliente activo para enviar la línea de log para evitar realizar el cálculo de token.
     *
     * @param agentLineLog
     * @return
     */
    private HttpClient findHttpClient(final AgentLineLog agentLineLog) {
        return clients.computeIfAbsent(agentLineLog.getAgentTokenId(),
                k -> new HttpClient(agentLineLog.getAuthenticationUrl(), agentLineLog.getPutLogUrl(), k));
    }

    /**
     * Introduce una línea a enviar en el worker.
     *
     * @param lineLog
     */
    private void putLogToSend(AgentLineLog lineLog) {

        logToSend.add(lineLog);
        checkThread();

    }

}
