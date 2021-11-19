package edu.uoc.tfgmonitorsystem.common.config;

import com.mongodb.MongoClientSettings.Builder;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import edu.uoc.tfgmonitorsystem.common.model.repository.AgentRepository;
import edu.uoc.tfgmonitorsystem.common.model.repository.CountryRepository;
import edu.uoc.tfgmonitorsystem.common.model.repository.SystemsRepository;
import edu.uoc.tfgmonitorsystem.common.model.repository.UserRepository;
import java.util.Arrays;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
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

    private static final Logger LOGGER = Logger.getLogger(MongoDBConfig.class);

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
        factory.setResources(new Resource[] { new ClassPathResource("test-data-user.json"),
                new ClassPathResource("test-data-system.json"), new ClassPathResource("test-data-agent.json") });
        return factory;
    }

    @Override
    protected void configureClientSettings(Builder builder) {

        builder.credential(MongoCredential.createCredential(username, database, password.toCharArray()))
                .applyToClusterSettings(settings -> {
                    settings.hosts(Arrays.asList(new ServerAddress(host, port)));
                });
    }

    /**
     * Comando de pruebas.
     *
     * @param systemRepository
     * @param countryRepository
     * @return
     */
    @Bean
    CommandLineRunner commandLineRunner(SystemsRepository systemRepository, CountryRepository countryRepository,
            UserRepository userRepository, AgentRepository agentRepository) {
        return strings -> {
            // systemRepository.save(new MonitorizableSystem(1, "Sistema 1", countryRepository.findById(1).get(),
            // true));

            LOGGER.debug("Country: " + countryRepository.findById(1).get());
            LOGGER.debug("User: " + userRepository.findById(10001).get());
            LOGGER.debug("Systems: " + systemRepository.findById(10001).get());
            LOGGER.debug("Agent: " + agentRepository.findById(10001).get());
        };
    }

}
