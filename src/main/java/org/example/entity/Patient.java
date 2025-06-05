package org.example.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Sharded;

import java.util.List;

@Document(collection = "patients")
@Sharded(shardKey = {"name"}) // Sharding on the "name" field
@CompoundIndex(name = "name_age_idx", def = "{'name': 1, 'age': -1}") // Compound index on "name" and "age"
@Data
public class Patient {
    @Id
    private String id;

    @TextIndexed // Full-text search enabled
    private String name;

    private int age;

    private String gender;

    @DBRef
    private Doctor doctor;

    @DBRef
    private List<Appointment> appointments;

    @Transient // Excluded from persistence
    private String tempData;
}