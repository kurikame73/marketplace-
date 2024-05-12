package com.example.marketplace.domain.Item.dto.request;

import lombok.Getter;

@Getter
public class RecommendationRequestDto {
    private Long itemId;
    private Long memberId;
}
