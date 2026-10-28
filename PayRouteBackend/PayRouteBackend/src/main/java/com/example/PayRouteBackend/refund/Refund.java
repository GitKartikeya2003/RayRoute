package com.example.PayRouteBackend.refund;


import com.example.PayRouteBackend.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "refunds",
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"merchantId", "idempotencyKey"}))
public class Refund extends BaseEntity {


    @Column(nullable = false, length = 48)
    private String paymentID;

    @Column(nullable = false, length = 100)
    private String idempotencyKey;

    @Column(nullable = false)
    public long amount;

    @Column(nullable = false, length = 20)
    public String status = "SUCCESS";


}
