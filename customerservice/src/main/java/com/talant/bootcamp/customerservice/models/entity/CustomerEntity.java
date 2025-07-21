package com.talant.bootcamp.customerservice.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "customers")
@EqualsAndHashCode
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false, unique = true)
    private String email;

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

    public String getEmail(){ return this.email; }

    public void setEmail(String email){ this.email = email; }

    public LocalDate getBirthday(){ return this.birthday; }

    public void setBirthday(LocalDate birthday){ this.birthday = birthday; }



}
