package com.example.marketplace.domain.cart.controller;

import com.example.marketplace.domain.cart.dto.response.AddItemToCartRequestDto;
import com.example.marketplace.domain.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addItemToCart(@RequestBody AddItemToCartRequestDto requestDto) {
        cartService.addItemToCart(requestDto);
        return ResponseEntity.ok("상품이 장바구니에 추가되었습니다.");
    }
}
