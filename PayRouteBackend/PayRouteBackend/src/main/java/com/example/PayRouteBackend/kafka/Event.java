package com.example.PayRouteBackend.kafka;


import com.example.PayRouteBackend.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "events")
public class Event extends BaseEntity {


    @Column(nullable = false, length = 48)
    public String merchantId;

    @Column(nullable = false, length = 48)
    public String type;

    @Column(nullable = false, columnDefinition = "TEXT")
    public String payload;

    public Instant publishedAt;


    public Instant consumedAt;
}
