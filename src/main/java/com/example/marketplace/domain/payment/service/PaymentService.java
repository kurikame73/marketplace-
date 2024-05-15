package com.example.marketplace.domain.payment.service;

import com.example.marketplace.domain.order.entity.Order;
import com.example.marketplace.domain.order.repository.OrderRepository;
import com.example.marketplace.domain.payment.dto.request.PaymentRequestDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {
    private final OrderRepository orderRepository;

    public void processPayment(PaymentRequestDto dto) {
        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없음."));

        // 재고 처리
        // 판매량 증가

    }
}
