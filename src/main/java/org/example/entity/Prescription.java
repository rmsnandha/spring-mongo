package org.example.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "prescriptions")
@CompoundIndex(name = "medication_dosage_idx", def = "{'medication': 1, 'dosage': 1}") // Compound index
@Data
public class Prescription {
    @Id
    private String id;

    @TextIndexed
    private String medication;

    private String dosage;

    private String instructions;

    @DBRef
    private Patient patient;

    @DBRef
    private Doctor doctor;

    @Transient
    private String tempData;
}