package com.example.marketplace.domain.cart.entity;

import com.example.marketplace.domain.Item.entity.Item;
import com.example.marketplace.domain.member.entity.Member;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
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
}
