package com.example.marketplace.domain.payment.service;

import com.example.marketplace.domain.Item.entity.Item;
import com.example.marketplace.domain.Item.repository.ItemRepository;
import com.example.marketplace.domain.cart.entity.Cart;
import com.example.marketplace.domain.cart.entity.CartItem;
import com.example.marketplace.domain.cart.repository.CartRepository;
import com.example.marketplace.domain.coupon.entity.Coupon;
import com.example.marketplace.domain.coupon.repository.CouponRepository;
import com.example.marketplace.domain.order.entity.Order;
import com.example.marketplace.domain.order.entity.OrderStatus;
import com.example.marketplace.domain.order.repository.OrderRepository;
import com.example.marketplace.domain.payment.dto.request.PaymentRequestDto;
import com.example.marketplace.domain.payment.entity.Payment;
import com.example.marketplace.domain.payment.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.marketplace.domain.coupon.entity.QCoupon.coupon;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final CouponRepository couponRepository;
    private final CartRepository cartRepository;
    private final PaymentRepository paymentRepository;

    public void processPayment(PaymentRequestDto dto) {
        // 검증
        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없음."));

        Cart cart = order.getCart();

        // 가격계산
        Double finalPrice = cart.calculateTotalPrice();

        // 쿠폰 적용
        if (dto.getCouponId() != null) {
            Coupon coupon = couponRepository.findById(dto.getCouponId())
                    .orElseThrow(() -> new EntityNotFoundException("쿠폰을 찾을 수 없습니다. ID: " + dto.getCouponId()));
            finalPrice = applyDiscount(finalPrice, coupon.getDiscountRate());
            coupon.markAsUsed();
            couponRepository.save(coupon);
        }

        // 모듈전송
        boolean paymentSuccessful = true;

        // 실패시 롤백
        if (!paymentSuccessful) {
            throw new RuntimeException("결제 실패.");
        }

        // 성공 시 재고처리
        for (CartItem cartItem : cart.getCartItems()) {
            Item item = cartItem.getItem();
            // TODO: kafka
            item.decreaseQuantity(cartItem.getQuantity());
            itemRepository.save(item);
        }

        // 주문 상태 업데이트
        order.finishOrder(finalPrice, OrderStatus.PAID);
        orderRepository.save(order);

        // 장바구니 비우기
        cart.clearItems();
        cartRepository.save(cart);

        // 결제 정보 저장
        paymentRepository.save(Payment.createPayment(finalPrice, dto.getPaymentMethod()));
    }

    // TODO: 쿠폰 쪽으로
    private double applyDiscount(double price, double discountRate) {
        return price * (1 - discountRate);
    }
}
