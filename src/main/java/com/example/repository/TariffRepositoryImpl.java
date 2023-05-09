package com.example.repository;

import com.example.entity.Tariff;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TariffRepositoryImpl implements TariffRepository {
    private final JdbcTemplate jdbcTemplate;

    public TariffRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }@Override
    public List<Tariff> findAll() {
        String sql = "SELECT * FROM tariff";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Tariff tariff = new Tariff();
            tariff.setId(rs.getLong("id"));
            tariff.setType(rs.getString("type"));
            tariff.setInterestRate(rs.getString("interest_rate"));
            return tariff;
        });
    }

    @Override
    public Optional<Tariff> findById(Long id) {
        String sql = "SELECT * FROM tariff WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Tariff tariff = new Tariff();
            tariff.setId(rs.getLong("id"));
            tariff.setType(rs.getString("type"));
            tariff.setInterestRate(rs.getString("interest_rate"));
            return Optional.of(tariff);
        });
    }

    @Override
    public Tariff save(Tariff tariff) {
        String sql = "INSERT INTO tariff (type, interest_rate) VALUES (?, ?)";
        jdbcTemplate.update(sql, tariff.getType(), tariff.getInterestRate());
        return tariff;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM tariff WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }}