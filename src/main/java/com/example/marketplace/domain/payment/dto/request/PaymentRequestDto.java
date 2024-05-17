package com.example.marketplace.domain.payment.dto.request;

import lombok.Getter;

@Getter
public class PaymentRequestDto {
    private Long orderId;
    private Long couponId;
}
