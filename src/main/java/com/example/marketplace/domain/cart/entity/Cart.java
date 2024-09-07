package com.example.marketplace.domain.cart.entity;

import com.example.marketplace.domain.Item.entity.Item;
import com.example.marketplace.domain.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL)
    private Member member;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();

    public void addItemToCart(Item item, Integer quantity) {
        for (CartItem addItem : cartItems) {
            if (addItem.getItem().equals(item)) {
                addItem.addQuantity(quantity);
                return;
            }
        }
        CartItem newItem = new CartItem(this, item, quantity);
        cartItems.add(newItem);
    }

    public void clearItems() {
        cartItems.clear();
    }

    public Double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            Item item = cartItem.getItem();
            Double itemPrice = Double.valueOf(item.getItemPrice());

            // 상품 자체할인 가격 계산
            Double discountRate = item.getDiscountRate() / 100.0;
            Double finalPrice = itemPrice - (itemPrice * discountRate);

            // 최종 가격에 수량 곱하기
            totalPrice += finalPrice * cartItem.getQuantity();
        }

        return totalPrice;
    }
}
