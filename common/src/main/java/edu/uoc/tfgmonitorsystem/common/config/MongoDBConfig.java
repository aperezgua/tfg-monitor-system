package edu.uoc.tfgmonitorsystem.common.config;

import com.mongodb.MongoClientSettings.Builder;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Ver https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mongo.mongo-db-factory-java
 */

@EnableMongoRepositories(basePackages = "edu.uoc.tfgmonitorsystem.common.model.repository")
@Configuration
//@EnableTransactionManagement
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

//    @Bean
//    MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
//        return new MongoTransactionManager(dbFactory);
//    }

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
}
