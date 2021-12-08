package edu.uoc.tfgmonitorsystem.log4j.appender;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Log4jWorker {

    private static Log4jWorker instance;

    private BlockingQueue<AgentLineLog> logToSend = new SynchronousQueue<>();

    private Map<String, HttpClient> clients = new ConcurrentHashMap<>();

    private AtomicBoolean running = new AtomicBoolean(true);

    /**
     * Thread principal encargado de consumir la cola.
     */
    private Thread thread = new Thread() {

        @Override
        public void run() {

            while (running.get()) {
                boolean send = false;
                AgentLineLog agentLineLog = null;
                try {
                    // elimina de la cola.
                    agentLineLog = logToSend.take();

                    HttpClient client = findHttpClient(agentLineLog);

                    if (client.putLogAndRetry(agentLineLog.getLogLine())) {
                        send = true;
                        System.out.println("Line send correctly: " + agentLineLog);
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

        };
    };

    private Log4jWorker() {
        super();
        thread.start();
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

    /**
     * Realiza un set del bucle principal del worker a false para que pare.
     */
    public void stop() {
        running.set(false);
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
        System.out.println("Put line to send " + lineLog);
        try {
            logToSend.put(lineLog);
        } catch (InterruptedException e) {
            System.out.println("Cannot putLogToSend. Interrupted exception");
            Thread.currentThread().interrupt();
        }
    }

}
