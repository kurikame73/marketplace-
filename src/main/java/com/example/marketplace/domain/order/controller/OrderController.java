package com.example.marketplace.domain.order.controller;

import com.example.marketplace.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<String> createOrder(@RequestParam Long memberId) {
        log.info("OrderController memberId = {}", memberId);
        orderService.createOrder(memberId);
        return ResponseEntity.ok("주문이 생성되었습니다.");
    }
}

