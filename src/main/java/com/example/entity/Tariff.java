package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String interestRate;

    public void setId(long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public String getType() {
        return type;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public Long getId(){return id;}
}
