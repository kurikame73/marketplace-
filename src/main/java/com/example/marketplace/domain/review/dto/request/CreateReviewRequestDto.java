package com.example.marketplace.domain.review.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreateReviewRequestDto {
    private Long itemId;
    private Long memberId;
    private String comment;
    private List<String> imageUrls;
}
