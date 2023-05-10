package com.example.service;

import com.example.entity.LoanOrder;
import com.example.repository.LoanOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class LoanOrderService {

    @Autowired
    private LoanOrderRepository loanOrderRepository;
    private final Random random = new Random();

    public List<LoanOrder> getAllLoanOrders() {
        return loanOrderRepository.findAll();
    }

    public LoanOrder createLoanOrder(LoanOrder loanOrder) {
        loanOrder.setStatus("IN_PROGRESS");
        loanOrder.setTimeInsert(new Timestamp(System.currentTimeMillis()));
        loanOrder.setTimeUpdate(new Timestamp(System.currentTimeMillis()));
        return loanOrderRepository.save(loanOrder);
    }

    public void deleteLoanOrder(Long userId, String orderId) {
        loanOrderRepository.deleteByUserIdAndOrderId(userId, orderId);
    }
    public String getLoanOrderStatus(String orderId) {
        return loanOrderRepository.getLoanOrderStatus(orderId);
    }
    @Scheduled(fixedRate = 120000) 
    public void processInProgressOrders() {
        List<LoanOrder> inProgressOrders = loanOrderRepository.findAllByStatus("IN_PROGRESS");
        for (LoanOrder order : inProgressOrders) {
            if (random.nextDouble() < 0.5) { 
                order.setStatus("APPROVED");
            } else {
                order.setStatus("REFUSED");
            }
            order.setTimeUpdate(new Timestamp(System.currentTimeMillis()));
            loanOrderRepository.deleteByStatus("IN_PROGRESS"); 
            loanOrderRepository.save(order);
        }
    }
}
