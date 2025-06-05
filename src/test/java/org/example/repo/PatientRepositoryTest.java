package org.example.repo;

import org.example.entity.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        patientRepository.deleteAll();

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

        patientRepository.saveAll(Arrays.asList(patient1, patient2, patient3));
    }

    @Test
    void testFindByName() {
        List<Patient> patients = patientRepository.findByName("John Doe");
        assertThat(patients).hasSize(1);
        assertThat(patients.get(0).getName()).isEqualTo("John Doe");
    }

    @Test
    void testFindByAgeGreaterThan() {
        List<Patient> patients = patientRepository.findByAgeGreaterThan(30);
        assertThat(patients).hasSize(1);
        assertThat(patients.get(0).getName()).isEqualTo("Alice");
    }

    @Test
    void testFindByNameIn() {
        List<Patient> patients = patientRepository.findByNameIn(Arrays.asList("John Doe", "Alice"));
        assertThat(patients).hasSize(2);
    }

    @Test
    void testFindByAgeGreaterThanEqual() {
        Query query = new Query(Criteria.where("age").gte(30)); // $gte
        List<Patient> patients = mongoTemplate.find(query, Patient.class);
        assertThat(patients).hasSize(2);
    }

    @Test
    void testFindByNameInUsingCriteria() {
        Query query = new Query(Criteria.where("name").in("John Doe", "Jane Doe")); // $in
        List<Patient> patients = mongoTemplate.find(query, Patient.class);
        assertThat(patients).hasSize(2);
    }
}