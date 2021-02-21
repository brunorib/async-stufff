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

    private static final String URI = "mongodb+srv://admin:<password>@cluster0.yp8ye.mongodb.net/cryptodata?retryWrites=true&w=majority";

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(URI);
    }

    public @Bean MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "cryptodata");
    }

}