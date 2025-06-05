package org.example.repo;

import lombok.Data;
import lombok.Value;
import org.example.entity.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@DataMongoTest
class PatientAggregationTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection(Patient.class);

        Patient patient1 = new Patient();
        patient1.setName("John Doe");
        patient1.setAge(30);
        patient1.setGender("Male");

        Patient patient2 = new Patient();
        patient2.setName("Jane Doe");
        patient2.setAge(25);
        patient2.setGender("Female");

        Patient patient3 = new Patient();
        patient3.setName("Alice");
        patient3.setAge(35);
        patient3.setGender("Female");

        mongoTemplate.insertAll(Arrays.asList(patient1, patient2, patient3));
    }

    @Test
    void testAggregationPipeline() {
        // Define the aggregation pipeline
        Aggregation aggregation = newAggregation(
                match(where("age").gte(30)), // Filter patients with age >= 30
                group("gender").count().as("count"), // Group by gender and count
                project("count").and("gender").previousOperation() // Project the fields
        );

        // Execute the aggregation
        AggregationResults<GenderCount> results = mongoTemplate.aggregate(aggregation, "patients", GenderCount.class);

        // Verify the results
        List<GenderCount> genderCounts = results.getMappedResults();
        assertThat(genderCounts).hasSize(2);

        GenderCount maleCount = genderCounts.stream().filter(gc -> gc.getGender().equals("Male")).findFirst().orElse(null);
        GenderCount femaleCount = genderCounts.stream().filter(gc -> gc.getGender().equals("Female")).findFirst().orElse(null);

        assertThat(maleCount).isNotNull();
        assertThat(maleCount.getCount()).isEqualTo(1);

        assertThat(femaleCount).isNotNull();
        assertThat(femaleCount.getCount()).isEqualTo(1);
    }

    // Helper class to map aggregation results
    @Value
    static class GenderCount {
        private String gender;
        private long count;
    }
}