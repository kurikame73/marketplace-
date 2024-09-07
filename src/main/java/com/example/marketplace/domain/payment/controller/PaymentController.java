package com.example.marketplace.domain.payment.controller;

import com.example.marketplace.domain.payment.dto.request.PaymentRequestDto;
import com.example.marketplace.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<String> processPayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        log.info("PaymentController request = {}", paymentRequestDto);
        paymentService.processPayment(paymentRequestDto);
        return ResponseEntity.ok("결제가 성공적으로 처리되었습니다.");
    }
}

