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

@Document(collection = "hospitals")
@Sharded(shardKey = {"name"}) // Sharding on the "name" field
@CompoundIndex(name = "name_address_idx", def = "{'name': 1, 'address': 1}") // Compound index
@Data
public class Hospital {
    @Id
    private String id;

    @TextIndexed
    private String name;

    private String address;

    @DBRef
    private List<Doctor> doctors;

    @Transient
    private String tempData;
}