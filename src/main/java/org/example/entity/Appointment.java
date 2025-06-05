package org.example.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "appointments")
@CompoundIndex(name = "date_reason_idx", def = "{'date': 1, 'reason': 1}") // Compound index on "date" and "reason"
@Data
public class Appointment {
    @Id
    private String id;

    private LocalDateTime date;

    private String reason;

    @DBRef
    private Patient patient;

    @DBRef
    private Doctor doctor;

    @Transient
    private String tempData;
}