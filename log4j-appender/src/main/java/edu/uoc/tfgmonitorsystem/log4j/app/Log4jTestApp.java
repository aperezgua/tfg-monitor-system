package edu.uoc.tfgmonitorsystem.log4j.app;

import java.security.NoSuchAlgorithmException;
import org.apache.log4j.Logger;

public class Log4jTestApp {

    private static final Logger LOGGER = Logger.getLogger(Log4jTestApp.class);

    public static void main(String[] args) throws NoSuchAlgorithmException {

        LOGGER.debug("Start app");

        Thread thread1 = new TestAppender1(300);
        thread1.start();

        Thread thread2 = new TestAppender2(300);
        thread2.start();

    }
}
