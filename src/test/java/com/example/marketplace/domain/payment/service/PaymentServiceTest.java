//package com.example.marketplace.domain.payment.service;
//
//import com.example.marketplace.MarketplaceApplication;
//import com.example.marketplace.domain.Item.entity.Item;
//import com.example.marketplace.domain.Item.repository.ItemRepository;
//import com.example.marketplace.domain.cart.entity.Cart;
//import com.example.marketplace.domain.cart.repository.CartRepository;
//import com.example.marketplace.domain.config.TestConfig;
//import com.example.marketplace.domain.coupon.entity.Coupon;
//import com.example.marketplace.domain.coupon.repository.CouponRepository;
//import com.example.marketplace.domain.member.entity.Member;
//import com.example.marketplace.domain.member.repository.MemberRepository;
//import com.example.marketplace.domain.order.entity.Order;
//import com.example.marketplace.domain.order.entity.OrderStatus;
//import com.example.marketplace.domain.order.repository.OrderRepository;
//import com.example.marketplace.domain.payment.dto.request.PaymentRequestDto;
//import com.example.marketplace.domain.payment.entity.PaymentMethod;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(classes = MarketplaceApplication.class)
//@Import(TestConfig.class)
//@ActiveProfiles("test")
//@Slf4j
//@Transactional
//class PaymentServiceTest {
//
//    @Autowired
//    private ItemRepository itemRepository;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private CartRepository cartRepository;
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Autowired
//    private CouponRepository couponRepository;
//
//    @Autowired
//    private PaymentService paymentService;
//
//    private Member member;
//    private Item item1;
//    private Item item2;
//    private Cart cart;
//    private Order order;
//    private Coupon coupon;
//
//    @BeforeEach
//    void setUp() {
//        member = Member.builder()
//                .name("Test User")
//                .email("test@example.com")
//                .password("password")
//                .cart(new Cart())
//                .build();
//        memberRepository.save(member);
//
//        cart = member.getCart();
//        cart.setMember(member);
//        cartRepository.save(cart);
//
//        item1 = Item.builder()
//                .itemName("Item 1")
//                .itemPrice(100)
//                .discountRate(10)
//                .stockQuantity(50)
//                .build();
//        itemRepository.save(item1);
//
//        item2 = Item.builder()
//                .itemName("Item 2")
//                .itemPrice(200)
//                .discountRate(20)
//                .stockQuantity(50)
//                .build();
//        itemRepository.save(item2);
//
//        cart.addItemToCart(item1, 2);
//        cart.addItemToCart(item2, 3);
//        cartRepository.save(cart);
//
//        order = Order.createOrder(cart);
//        orderRepository.save(order);
//
//        coupon = Coupon.builder()
//                .discountRate(15.0)
//                .isUsed(false)
//                .build();
//        couponRepository.save(coupon);
//    }
//
//    @Test
//    public void testOrderPaymentWithCoupon() {
//        PaymentRequestDto paymentRequestDto = PaymentRequestDto.builder()
//                .orderId(order.getId())
//                .couponId(coupon.getId())
//                .paymentMethod(PaymentMethod.A)
//                .build();
//
//        double item1Price = item1.getItemPrice() - (item1.getItemPrice() * (item1.getDiscountRate() / 100.0));
//        double item2Price = item2.getItemPrice() - (item2.getItemPrice() * (item2.getDiscountRate() / 100.0));
//        double expectedTotalPriceBeforeCoupon = (item1Price * 2) + (item2Price * 3);
//        double expectedTotalPriceAfterCoupon = expectedTotalPriceBeforeCoupon * (1 - (coupon.getDiscountRate() / 100.0));
//
//        log.info("Item 1 Price After Discount: {}", item1Price);
//        log.info("Item 2 Price After Discount: {}", item2Price);
//        log.info("Expected Total Price Before Coupon: {}", expectedTotalPriceBeforeCoupon);
//        log.info("Expected Total Price After Coupon: {}", expectedTotalPriceAfterCoupon);
//
//        paymentService.processPayment(paymentRequestDto);
//
//        Order paidOrder = orderRepository.findById(order.getId()).orElseThrow();
//        Cart emptyCart = cartRepository.findById(cart.getId()).orElseThrow();
//        Coupon usedCoupon = couponRepository.findById(coupon.getId()).orElseThrow();
//
//        assertThat(paidOrder.getStatus()).isEqualTo(OrderStatus.PAID);
//        assertThat(emptyCart.getCartItems()).isNotNull();
//        assertThat(usedCoupon.getIsUsed()).isTrue();
//        assertThat(paidOrder.getPaymentAmount()).isEqualTo(expectedTotalPriceAfterCoupon);
//    }
//
//}