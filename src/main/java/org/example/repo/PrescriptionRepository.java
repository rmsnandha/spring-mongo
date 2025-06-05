package org.example.repo;

import org.example.entity.Prescription;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrescriptionRepository extends MongoRepository<Prescription, String> {
}