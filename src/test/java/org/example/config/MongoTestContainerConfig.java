package org.example.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.MongoDBContainer;

@Configuration
public class MongoTestContainerConfig {

    @Bean
    public MongoDBContainer mongoDBContainer() {
        MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0");
        mongoDBContainer.start();
        return mongoDBContainer;
    }
}