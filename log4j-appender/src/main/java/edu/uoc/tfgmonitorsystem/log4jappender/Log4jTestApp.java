package edu.uoc.tfgmonitorsystem.log4jappender;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.apache.log4j.Logger;

public class Log4jTestApp {

    private static final Logger LOGGER = Logger.getLogger(Log4jTestApp.class);

    private static final String[] LOGS_TEST = { "Se ha producido un error en el sistema", "Nivel crítico",
            "NullPointerException" };

    public static void main(String[] args) throws NoSuchAlgorithmException {

        SecureRandom random = SecureRandom.getInstanceStrong();

        Thread thread = new Thread() {

            @Override
            public void run() {
                while (true) {
                    LOGGER.debug(LOGS_TEST[random.nextInt(LOGS_TEST.length)]);
                    LOGGER.debug("La ejecución ha tardado " + random.nextInt(100) + "s");
                    try {
                        Thread.sleep(random.nextInt(10) * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        };

        thread.start();

    }
}
