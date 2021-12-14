package edu.uoc.tfgmonitorsystem.logmicroservice;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
@ComponentScan({ "edu.uoc.tfgmonitorsystem" })
public class LogMicroserviceApp {

    private static final Logger LOGGER = Logger.getLogger(LogMicroserviceApp.class);

    public static void main(String[] args) {
        LOGGER.debug(".........Start APP.........");
        SpringApplication.run(LogMicroserviceApp.class, args);
    }

}
