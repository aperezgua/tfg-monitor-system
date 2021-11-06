package edu.uoc.tfgmonitorsystem.usermicroservice.config;

import com.mongodb.MongoClientSettings.Builder;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import edu.uoc.tfgmonitorsystem.common.model.document.User;
import edu.uoc.tfgmonitorsystem.common.model.repository.UserRepository;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

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
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return strings -> {
            userRepository.save(new User(1, "Abel", "abelperezgua@uoc.edu", "pw"));
            userRepository.save(new User(2, "Sam", "sam@uoc.edu", "pw"));
        };
    }

}
