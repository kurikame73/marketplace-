package com.example.marketplace.domain.coupon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private double discountRate;

    private Boolean isUsed;

    public void markAsUsed() {
        if (this.isUsed) {
            throw new RuntimeException("이미 사용된 쿠폰입니다.");
        }
        this.isUsed = true;
    }
}
