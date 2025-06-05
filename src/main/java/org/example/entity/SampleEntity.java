package org.example.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class SampleEntity {
    @Id
    private String id;
    private String name;

    // Getters and setters
}