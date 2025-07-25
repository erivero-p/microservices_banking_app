package com.talant.bootcamp.customerservice.models.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "customers")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @Column(nullable = false)
    private String name;

    // Default constructor
    public CustomerEntity() {}

    // Constructor
    public CustomerEntity(String name) {
        this.name = name;
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
