package com.example.repository;

import com.example.entity.Tariff;

import java.util.List;
import java.util.Optional;

public interface TariffRepository {
    List<Tariff> findAll();
    Optional<Tariff> findById(Long id);
    Tariff save(Tariff tariff);
    void deleteById(Long id);
}