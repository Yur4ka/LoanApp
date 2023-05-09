package com.example.service;

import com.example.entity.Tariff;
import com.example.repository.TariffRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TariffService {
    private final TariffRepository tariffRepository;

    public TariffService(TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }


    public List<Tariff> getAllTariffs() {
        return tariffRepository.findAll();
    }


    public Tariff getTariffById(Long id) {
        Optional<Tariff> tariff = tariffRepository.findById(id);
        if (tariff.isPresent()) {
            return tariff.get();
        } else {
            throw new EntityNotFoundException("Tariff not found with id: " + id);
        }
    }


    public Tariff saveTariff(Tariff tariff) {
        tariffRepository.save(tariff);
        return tariff;
    }

    public boolean existsById(Long id) {
        return tariffRepository.findById(id).isPresent();
    }
    public void deleteTariffById(Long id) {
        tariffRepository.deleteById(id);
    }
}
