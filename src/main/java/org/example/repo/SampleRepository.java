package org.example.repo;
import org.example.entity.SampleEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SampleRepository extends MongoRepository<SampleEntity, String> {
}