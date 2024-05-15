package com.example.marketplace.domain.order.service;

import com.example.marketplace.domain.cart.entity.Cart;
import com.example.marketplace.domain.cart.repository.CartRepository;
import com.example.marketplace.domain.order.entity.Order;
import com.example.marketplace.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    public void createOrder(Long memberId) {
        Cart cart = cartRepository.findByMemberId(memberId).orElseThrow();
        orderRepository.save(Order.createOrder(cart));
    }
}
