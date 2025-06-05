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

@Document(collection = "doctors")
@Sharded(shardKey = {"specialization"}) // Sharding on the "specialization" field
@CompoundIndex(name = "name_specialization_idx", def = "{'name': 1, 'specialization': 1}") // Compound index
@Data
public class Doctor {
    @Id
    private String id;

    @TextIndexed
    private String name;

    private String specialization;

    @DBRef
    private List<Patient> patients;

    @DBRef
    private Hospital hospital;

    @Transient
    private String tempData;
}