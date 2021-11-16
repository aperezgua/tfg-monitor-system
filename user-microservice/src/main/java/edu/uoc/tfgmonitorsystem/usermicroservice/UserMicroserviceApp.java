package edu.uoc.tfgmonitorsystem.usermicroservice;

import edu.uoc.tfgmonitorsystem.common.model.document.Rol;
import edu.uoc.tfgmonitorsystem.common.model.document.User;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import edu.uoc.tfgmonitorsystem.usermicroservice.model.service.IUserService;
import java.util.Date;
import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({ "edu.uoc.tfgmonitorsystem" })
public class UserMicroserviceApp {

    private static final Logger LOGGER = Logger.getLogger(UserMicroserviceApp.class);

    public static void main(String[] args) {
        LOGGER.debug(".........Start APP.........");
        SpringApplication.run(UserMicroserviceApp.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(IUserService userService) {
        return strings -> {

            try {
                userService.createOrUpdate(
                        new User(null, "Abel", "aperezgua@uoc.edu", "pw", new Date(), true, Rol.ADMINISTRATOR));

                userService.createOrUpdate(new User(null, "Sam", "sam@uoc.edu", "pw", new Date(), true, Rol.SUPPORT));
            } catch (TfgMonitorSystenException exception) {
                LOGGER.warn("Cannot insert users", exception);
            }
        };
    }
}
