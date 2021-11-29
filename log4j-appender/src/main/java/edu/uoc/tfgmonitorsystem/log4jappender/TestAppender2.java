package edu.uoc.tfgmonitorsystem.log4jappender;

import java.security.NoSuchAlgorithmException;
import org.apache.log4j.Logger;

public class TestAppender2 extends AbstractTestAppender {

    private static final String[] LOGS_TEST = { "El sistema no responde", "Password incorrecta",
            "NoSuchElementException" };

    public TestAppender2(int maxTimeInSeconds) throws NoSuchAlgorithmException {
        super(LOGS_TEST, maxTimeInSeconds);
    }

    @Override
    protected Logger getLogger() {
        return Logger.getLogger(TestAppender2.class);
    }
}
