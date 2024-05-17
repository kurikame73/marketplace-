package com.example.marketplace.domain.payment.repository;

import com.example.marketplace.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
