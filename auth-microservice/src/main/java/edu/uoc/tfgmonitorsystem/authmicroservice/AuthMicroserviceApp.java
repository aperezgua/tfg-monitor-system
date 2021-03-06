package edu.uoc.tfgmonitorsystem.authmicroservice;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({ "edu.uoc.tfgmonitorsystem" })
public class AuthMicroserviceApp {

    private static final Logger LOGGER = Logger.getLogger(AuthMicroserviceApp.class);

    public static void main(String[] args) {
        LOGGER.debug(".........Start Auth Microservice.........");
        SpringApplication.run(AuthMicroserviceApp.class, args);
    }

}
