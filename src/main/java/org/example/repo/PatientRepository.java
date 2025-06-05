package org.example.repo;

import org.example.entity.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PatientRepository extends MongoRepository<Patient, String> {


    // $eq: Find patients with a specific name
    List<Patient> findByName(String name);

    // $ne: Find patients whose name is not equal to a specific value
    List<Patient> findByNameNot(String name);

    // $gt: Find patients older than a specific age
    List<Patient> findByAgeGreaterThan(int age);

    // $gte: Find patients older than or equal to a specific age
    List<Patient> findByAgeGreaterThanEqual(int age);

    // $lt: Find patients younger than a specific age
    List<Patient> findByAgeLessThan(int age);

    // $lte: Find patients younger than or equal to a specific age
    List<Patient> findByAgeLessThanEqual(int age);

    // $in: Find patients whose name is in a list of names
    List<Patient> findByNameIn(List<String> names);
}