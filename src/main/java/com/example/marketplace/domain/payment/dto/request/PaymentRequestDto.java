package com.example.marketplace.domain.payment.dto.request;

import com.example.marketplace.domain.payment.entity.PaymentMethod;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentRequestDto {
    private Long orderId;
    private Long couponId;
    private PaymentMethod paymentMethod;
}
