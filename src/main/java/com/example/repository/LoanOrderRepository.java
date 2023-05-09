package com.example.repository;

import com.example.entity.LoanOrder;

import java.util.List;
import java.util.Optional;
import java.sql.Timestamp;

public interface LoanOrderRepository {
    List<LoanOrder> findAll();
    LoanOrder save(LoanOrder loanOrder);
    void deleteByUserIdAndOrderId(Long userId, String orderId);
    String getLoanOrderStatus(String orderId);

    List<LoanOrder> findAllByStatus(String status);
    void deleteByStatus(String status);
    void updateStatusAndTimeById(Long id, String status, Timestamp timeUpdate);
}