package com.example.marketplace.domain.coupon.repository;

import com.example.marketplace.domain.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

}
