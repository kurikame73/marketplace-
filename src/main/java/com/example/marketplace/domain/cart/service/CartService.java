package com.example.marketplace.domain.cart.service;

import com.example.marketplace.domain.Item.entity.Item;
import com.example.marketplace.domain.Item.repository.ItemRepository;
import com.example.marketplace.domain.cart.dto.response.AddItemToCartRequestDto;
import com.example.marketplace.domain.cart.entity.Cart;
import com.example.marketplace.domain.cart.repository.CartRepository;
import com.example.marketplace.domain.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    public void addItemToCart(AddItemToCartRequestDto dto) {
        Item item = itemRepository.findById(dto.getItemId()).orElseThrow();
        Cart cart = cartRepository.findByMemberId(dto.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("카트를 찾을 수 없음."));
        cart.addItemToCart(item,dto.getQuantity());
        cartRepository.save(cart);
    }
}
