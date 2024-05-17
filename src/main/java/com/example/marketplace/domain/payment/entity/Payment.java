package com.example.marketplace.domain.payment.entity;

import com.example.marketplace.domain.order.entity.Order;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private BigDecimal amount;

//    @Enumarated(EnumType.STRING)
//    private PaymentMethod method;

    private LocalDateTime paymentDate;

}
