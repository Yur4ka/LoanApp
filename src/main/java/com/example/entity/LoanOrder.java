package com.example.entity;

import jakarta.persistence.*;


import java.sql.Timestamp;

@Entity
public class LoanOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderId;
    private Long userId;
    private Long tariffId;
    private Double creditRating;
    private String status;
    private Timestamp timeInsert;
    private Timestamp timeUpdate;

    public void setId(long id) {
        this.id = id;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setTariffId(long tariffId) {
        this.tariffId = tariffId;
    }

    public void setCreditRating(double creditRating) {
        this.creditRating = creditRating;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTimeInsert(Timestamp timeInsert) {
        this.timeInsert = timeInsert;
    }

    public void setTimeUpdate(Timestamp timeUpdate) {
        this.timeUpdate = timeUpdate;
    }

    public Object getOrderId() {
        return orderId;
    }

    public Object getUserId() {
        return userId;
    }

    public Object getTariffId() {
        return tariffId;
    }

    public Object getCreditRating() {
        return creditRating;
    }

    public Object getStatus() {
        return status;
    }

    public Object getTimeInsert() {
        return timeInsert;
    }

    public Object getTimeUpdate() {
        return timeUpdate;
    }

    public Long getId() {
        return id;
    }
}
