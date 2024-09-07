package com.example.marketplace.domain.payment.dto.request;

import com.example.marketplace.domain.payment.entity.PaymentMethod;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto {
    private Long orderId;

    private Long couponId;

    private PaymentMethod paymentMethod;

    public boolean hasCoupon() {
        return couponId != null;
    }
}
