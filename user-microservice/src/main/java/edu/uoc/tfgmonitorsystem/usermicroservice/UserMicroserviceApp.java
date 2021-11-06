package edu.uoc.tfgmonitorsystem.usermicroservice;

import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
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

    /**
     * Explora los beans definidos en el contexto y que han sido declarados para sacar una línea de log con su nombre.
     * 
     * @param context Contexto del aplicativo.
     * @return CommandLineRunner código encapsulado que será ejecutado por spring-boot usando patrón commander.
     */
    @Bean
    public CommandLineRunner checkDefinedBeans(ApplicationContext context) {

        CommandLineRunner commandLineRunner = new CommandLineRunner() {

            @Override
            public void run(String... args) throws Exception {
                LOGGER.debug("Check beans:");

                for (String beanName : context.getBeanDefinitionNames()) {

                    LOGGER.debug(" >> " + beanName);

                }

            }

        };

        return commandLineRunner;

    }

}
