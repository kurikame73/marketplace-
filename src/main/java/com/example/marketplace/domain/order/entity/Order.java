package com.example.marketplace.domain.order.entity;

import com.example.marketplace.domain.cart.entity.Cart;
import com.example.marketplace.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;

    private LocalDateTime orderDate;

    public static Order createOrder(Cart cart) {
        return Order.builder()
                .cart(cart)
                .status(OrderStatus.PENDING)
                .orderDate(LocalDateTime.now())
                .member(cart.getMember())
                .build();
    }
}
