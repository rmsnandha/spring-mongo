package org.example.repo;

import org.example.entity.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DoctorRepository extends MongoRepository<Doctor, String> {
}