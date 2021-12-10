package edu.uoc.tfgmonitorsystem.log4j.app;

import java.security.NoSuchAlgorithmException;
import java.util.Random;
import org.apache.log4j.Logger;

public abstract class AbstractTestAppender extends Thread {

    private final Random random;

    private int maxSeconds = 0;

    private final String[] logsTest;

    public AbstractTestAppender(String[] logsTest, int maxSeconds) throws NoSuchAlgorithmException {
        random = new Random();
        this.logsTest = logsTest;
        this.maxSeconds = maxSeconds;
    }

    @Override
    public void run() {

        int accumulatedTime = 0;

        while (accumulatedTime <= maxSeconds) {

            getLogger().debug(logsTest[random.nextInt(logsTest.length)]);
            getLogger().debug("La ejecución ha tardado " + random.nextInt(10) + "s");

            long sleepTime = random.nextInt(5);

            try {
                Thread.sleep(sleepTime * 300);
                accumulatedTime += sleepTime;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract Logger getLogger();
}
