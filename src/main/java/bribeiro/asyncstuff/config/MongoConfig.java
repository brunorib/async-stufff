package bribeiro.asyncstuff.config;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    private static final String LOCAL_URI = "mongodb://localhost:27017";

    @Autowired
    private Environment env;

    @Bean
    public MongoClient mongoClient() {
        String uri = env.getProperty("MONGO_URI");

        if (uri == null) {
            uri = LOCAL_URI;
        }

        return MongoClients.create(uri);
    }

    public @Bean MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "cryptodata");
    }

}