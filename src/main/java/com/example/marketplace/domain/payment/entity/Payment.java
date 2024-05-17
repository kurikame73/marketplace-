package com.example.marketplace.domain.payment.entity;

import com.example.marketplace.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    private LocalDateTime paymentDate;

    public static Payment createPayment(Double paymentAmount, PaymentMethod paymentMethod) {
        return Payment.builder()
                .amount(paymentAmount)
                .method(paymentMethod)
                .paymentDate(LocalDateTime.now())
                .build();
    }
}
