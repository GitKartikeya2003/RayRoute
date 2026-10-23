package com.example.PayRouteBackend.common;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.Instant;

@Entity
public class BaseEntity {


    @Id
    @Column
    public String id;

    @Column(nullable = false , updatable = false)
    public Instant createdAt = Instant.now();

}
