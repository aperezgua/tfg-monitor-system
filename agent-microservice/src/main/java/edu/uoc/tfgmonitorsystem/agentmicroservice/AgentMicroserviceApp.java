package edu.uoc.tfgmonitorsystem.agentmicroservice;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({ "edu.uoc.tfgmonitorsystem" })
public class AgentMicroserviceApp {

    private static final Logger LOGGER = Logger.getLogger(AgentMicroserviceApp.class);

    public static void main(String[] args) {
        LOGGER.debug(".........Start Agent Microservice.........");
        SpringApplication.run(AgentMicroserviceApp.class, args);
    }

}
