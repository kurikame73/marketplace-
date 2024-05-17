package com.example.marketplace.domain.order.entity;

import com.example.marketplace.domain.cart.entity.Cart;
import com.example.marketplace.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Double paymentAmount;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate;

    public static Order createOrder(Cart cart) {
        return Order.builder()
                .cart(cart)
                .status(OrderStatus.PENDING)
                .orderDate(LocalDateTime.now())
                .member(cart.getMember())
                .paymentAmount(0.0)
                .build();
    }

    public void finishOrder(Double paymentAmount, OrderStatus status) {
        this.paymentAmount = paymentAmount;
        this.status = status;
    }
}
