package edu.uoc.tfgmonitorsystem.systemmicroservice;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({ "edu.uoc.tfgmonitorsystem" })
public class SystemMicroserviceApp {

    private static final Logger LOGGER = Logger.getLogger(SystemMicroserviceApp.class);

    public static void main(String[] args) {
        LOGGER.debug(".........Start APP.........");
        SpringApplication.run(SystemMicroserviceApp.class, args);
    }

}
