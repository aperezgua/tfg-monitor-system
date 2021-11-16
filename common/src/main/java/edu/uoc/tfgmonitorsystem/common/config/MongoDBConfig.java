package edu.uoc.tfgmonitorsystem.common.config;

import com.mongodb.MongoClientSettings.Builder;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import edu.uoc.tfgmonitorsystem.common.model.document.Rol;
import edu.uoc.tfgmonitorsystem.common.model.document.User;
import edu.uoc.tfgmonitorsystem.common.model.repository.UserRepository;
import edu.uoc.tfgmonitorsystem.common.model.service.IDbSequenceService;
import java.util.Arrays;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Ver https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mongo.mongo-db-factory-java
 */
@EnableMongoRepositories(basePackageClasses = UserRepository.class)
@Configuration
public class MongoDBConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.database}")
    private String database;
    @Value("${spring.data.mongodb.port}")
    private int port;
    @Value("${spring.data.mongodb.host}")
    private String host;
    @Value("${spring.data.mongodb.userCredentials.username}")
    private String username;
    @Value("${spring.data.mongodb.userCredentials.password}")
    private String password;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String getDatabaseName() {
        return database;
    }

    @Override
    protected void configureClientSettings(Builder builder) {

        builder.credential(MongoCredential.createCredential(username, database, password.toCharArray()))
                .applyToClusterSettings(settings -> {
                    settings.hosts(Arrays.asList(new ServerAddress(host, port)));
                });
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, IDbSequenceService sequenceService) {
        return strings -> {

            userRepository.save(new User(sequenceService.generateDbSequence(User.SEQUENCE_NAME), "Abel",
                    "aperezgua@uoc.edu", passwordEncoder.encode("pw"), new Date(), true, Rol.ADMINISTRATOR));
            userRepository.save(new User(sequenceService.generateDbSequence(User.SEQUENCE_NAME), "Sam", "sam@uoc.edu",
                    passwordEncoder.encode("pw"), new Date(), true, Rol.SUPPORT));
        };
    }
}
