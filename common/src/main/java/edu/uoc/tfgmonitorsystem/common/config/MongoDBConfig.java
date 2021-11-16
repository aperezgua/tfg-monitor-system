package edu.uoc.tfgmonitorsystem.common.config;

import com.mongodb.MongoClientSettings.Builder;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

@EnableMongoRepositories(basePackages = "edu.uoc.tfgmonitorsystem.common.model.repository")
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

    @Bean
    public Jackson2RepositoryPopulatorFactoryBean getRepositoryPopulator() {
        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
        factory.setResources(new Resource[] { new ClassPathResource("test-data.json") });
        return factory;
    }

    @Override
    protected void configureClientSettings(Builder builder) {

        builder.credential(MongoCredential.createCredential(username, database, password.toCharArray()))
                .applyToClusterSettings(settings -> {
                    settings.hosts(Arrays.asList(new ServerAddress(host, port)));
                });
    }

}
