package org.example.repo;

import org.example.entity.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HospitalRepository extends MongoRepository<Hospital, String> {
}