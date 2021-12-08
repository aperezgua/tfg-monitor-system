package edu.uoc.tfgmonitorsystem.webapp;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebApp {

    private static final Logger LOGGER = Logger.getLogger(WebApp.class);

    public static void main(String[] args) {
        LOGGER.debug("......... Start Webapp .........");
        SpringApplication.run(WebApp.class, args);
    }

}
