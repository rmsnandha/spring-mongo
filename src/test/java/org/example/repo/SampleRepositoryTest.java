package org.example.repo;

import org.example.entity.SampleEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class SampleRepositoryTest {

    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0");

    static {
        mongoDBContainer.start();
    }

    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private SampleRepository sampleRepository;

    @Test
    void testSaveAndFind() {
        SampleEntity entity = new SampleEntity();
        entity.setName("Test Name");
        sampleRepository.save(entity);

        assertThat(sampleRepository.findAll()).hasSize(1);
    }
}