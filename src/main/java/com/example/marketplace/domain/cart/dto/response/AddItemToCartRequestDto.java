package com.example.marketplace.domain.cart.dto.response;

import lombok.Getter;

@Getter
public class AddItemToCartRequestDto {
    private Long itemId;
    private Long memberId;
    private Integer quantity;
}
