package com.example.controller;

import com.example.entity.LoanOrder;
import com.example.entity.Tariff;
import com.example.service.LoanOrderService;
import com.example.service.TariffService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import jakarta.validation.Valid;
import org.apache.commons.lang3.concurrent.CircuitBreaker;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/loans")
@ControllerAdvice
public class LoanController {
    private final TariffService tariffService;
    private final LoanOrderService loanOrderService;


    public LoanController(TariffService tariffService, LoanOrderService loanOrderService) {
        this.tariffService = tariffService;
        this.loanOrderService = loanOrderService;
    }

    @GetMapping("/tariffs")
    public ResponseEntity<List<Tariff>> getAllTariffs() {
        List<Tariff> tariffs = tariffService.getAllTariffs();
        return ResponseEntity.ok(tariffs);
    }

    @GetMapping("/tariffs/{id}")
    public ResponseEntity<Tariff> getTariffById(@PathVariable Long id) {
        Tariff tariff = tariffService.getTariffById(id);
        return ResponseEntity.ok(tariff);
    }

    @PostMapping("/tariffs")
    public ResponseEntity<Tariff> createTariff(@RequestBody Tariff tariff) {
        Tariff savedTariff = tariffService.saveTariff(tariff);
        return ResponseEntity.ok(savedTariff);
    }

    @DeleteMapping("/tariffs/{id}")
    public ResponseEntity<?> deleteTariffById(@PathVariable Long id) {
        tariffService.deleteTariffById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/orders")
    public ResponseEntity<?> createLoanOrder(@RequestBody @Valid LoanOrder loanOrder, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        Long tariffId = (Long) loanOrder.getTariffId();
        if (!tariffService.existsById(tariffId)) {
            String errorMessage = String.format("Tariff with id %d does not exist", tariffId);
            return ResponseEntity.badRequest().body("Error: " + errorMessage);
        }

        loanOrder.setOrderId(UUID.randomUUID().toString());
        double creditRating = Math.round((0.1 + Math.random() * 0.8) * 100) / 100.0;
        loanOrder.setCreditRating(creditRating);
        LoanOrder savedLoanOrder = loanOrderService.createLoanOrder(loanOrder);
        return ResponseEntity.ok(savedLoanOrder);
    }

    @GetMapping("/orders")
    public List<LoanOrder> getAllLoanOrders() {
        return loanOrderService.getAllLoanOrders();
    }

    @DeleteMapping("/users/{userId}/orders/{orderId}")
    public ResponseEntity<?> deleteLoanOrder(@PathVariable Long userId, @PathVariable String orderId) {
        loanOrderService.deleteLoanOrder(userId, orderId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        return ResponseEntity.badRequest().body(result.getAllErrors());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
        String message = ex.getMessage();
        return ResponseEntity.notFound().build();
    }
}
