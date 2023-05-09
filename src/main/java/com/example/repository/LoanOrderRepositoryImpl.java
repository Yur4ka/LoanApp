package com.example.repository;

import com.example.entity.LoanOrder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.sql.Timestamp;

@Repository
public class LoanOrderRepositoryImpl implements LoanOrderRepository {
    private final JdbcTemplate jdbcTemplate;public LoanOrderRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<LoanOrder> findAll() {
        String sql = "SELECT * FROM loan_order";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            LoanOrder loanOrder = new LoanOrder();
            loanOrder.setId(rs.getLong("id"));
            loanOrder.setOrderId(rs.getString("order_id"));
            loanOrder.setUserId(rs.getLong("user_id"));
            loanOrder.setTariffId(rs.getLong("tariff_id"));
            loanOrder.setCreditRating(rs.getDouble("credit_rating"));
            loanOrder.setStatus(rs.getString("status"));
            loanOrder.setTimeInsert(rs.getTimestamp("time_insert"));
            loanOrder.setTimeUpdate(rs.getTimestamp("time_update"));
            return loanOrder;
        });
    }

    @Override
    public String getLoanOrderStatus(String orderId) {
        String sql = "SELECT status FROM loan_order WHERE order_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{orderId}, String.class);
    }

    @Override
    public LoanOrder save(LoanOrder loanOrder) {
        String sql = "INSERT INTO loan_order (order_id, user_id, tariff_id, credit_rating, status, time_insert, time_update) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, loanOrder.getOrderId(), loanOrder.getUserId(), loanOrder.getTariffId(),
                loanOrder.getCreditRating(), loanOrder.getStatus(), loanOrder.getTimeInsert(), loanOrder.getTimeUpdate());
        return loanOrder;
    }

    @Override
    public void deleteByUserIdAndOrderId(Long userId, String orderId) {
        String sql = "DELETE FROM loan_order WHERE user_id = ? AND order_id = ?";
        jdbcTemplate.update(sql, userId, orderId);
    }
    @Override
    public List<LoanOrder> findAllByStatus(String status) {
        String sql = "SELECT * FROM loan_order WHERE status = ?";
        return jdbcTemplate.query(sql, new Object[]{status}, (rs, rowNum) -> {
            LoanOrder loanOrder = new LoanOrder();
            loanOrder.setId(rs.getLong("id"));
            loanOrder.setOrderId(rs.getString("order_id"));
            loanOrder.setUserId(rs.getLong("user_id"));
            loanOrder.setTariffId(rs.getLong("tariff_id"));
            loanOrder.setCreditRating(rs.getDouble("credit_rating"));
            loanOrder.setStatus(rs.getString("status"));
            loanOrder.setTimeInsert(rs.getTimestamp("time_insert"));
            loanOrder.setTimeUpdate(rs.getTimestamp("time_update"));
            return loanOrder;
        });
    }

    @Override
    public void updateStatusAndTimeById(Long id, String status, Timestamp timeUpdate) {
        String sql = "UPDATE loan_order SET status = ?, time_update = ? WHERE id = ?";
        jdbcTemplate.update(sql, status, timeUpdate, id);
    }
    @Override
    public void deleteByStatus(String status) {
        String sql = "DELETE FROM loan_order WHERE status = ?";
        jdbcTemplate.update(sql, status);
    }
}