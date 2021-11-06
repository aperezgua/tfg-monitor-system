package edu.uoc.tfgmonitorsystem.usermicroservice;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserMicroserviceApp {

    private static final Logger LOGGER = Logger.getLogger(UserMicroserviceApp.class);

    public static void main(String[] args) {
        LOGGER.debug(".........Start APP.........");
        SpringApplication.run(UserMicroserviceApp.class, args);
    }

}
