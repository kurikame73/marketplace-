package com.example.marketplace.listener.item;

public record ItemRecommendedEvent(
        Long itemId,
        Long memberId) {
}
