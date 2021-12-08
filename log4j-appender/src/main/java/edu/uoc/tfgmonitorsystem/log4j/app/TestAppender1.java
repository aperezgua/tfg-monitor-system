package edu.uoc.tfgmonitorsystem.log4j.app;

import java.security.NoSuchAlgorithmException;
import org.apache.log4j.Logger;

public class TestAppender1 extends AbstractTestAppender {
    private static final String[] LOGS_TEST = { "Se ha producido un error en el sistema", "Nivel crítico",
            "NullPointerException" };

    public TestAppender1(int maxTimeInSeconds) throws NoSuchAlgorithmException {
        super(LOGS_TEST, maxTimeInSeconds);
    }

    @Override
    protected Logger getLogger() {
        return Logger.getLogger(TestAppender1.class);
    }
}
