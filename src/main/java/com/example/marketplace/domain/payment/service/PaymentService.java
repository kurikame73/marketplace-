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

        // 쿠폰 조회
        Coupon coupon = couponRepository.findById(dto.getCouponId()).orElseThrow();

        // 가격계산
        Double finalPrice = cart.calculateTotalPrice(coupon.getDiscountRate());

        // 모듈전송
        boolean paymentSuccessful = true;

        // 실패시 롤백
        if (!paymentSuccessful) {
            throw new RuntimeException("결제 실패.");
        }

        // 성공 시 재고처리
        for (CartItem cartItem : cart.getCartItems()) {
            Item item = cartItem.getItem();
            item.decreaseQuantity(cartItem.getQuantity());
            itemRepository.save(item);
        }
        order.finishOrder(finalPrice, OrderStatus.PAID);
        cart.clearItems();
        coupon.markAsUsed();

        paymentRepository.save(Payment.createPayment(finalPrice, dto.getPaymentMethod()));
        couponRepository.save(coupon);
        orderRepository.save(order);
        cartRepository.save(cart);
    }
}
