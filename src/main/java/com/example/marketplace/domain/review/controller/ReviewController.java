package com.example.marketplace.domain.review.controller;

import com.example.marketplace.domain.review.dto.request.CreateReviewRequestDto;
import com.example.marketplace.domain.review.dto.response.ItemReviewResponseDto;
import com.example.marketplace.domain.review.dto.response.MemberReviewResponseDto;
import com.example.marketplace.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ResponseEntity<String> createReview(CreateReviewRequestDto dto) {
        reviewService.createReview(dto);
        return ResponseEntity.ok("리뷰 등록 성공.");
    }

    public ResponseEntity<ItemReviewResponseDto> getItemReview(Long itemId) {
        return ResponseEntity.ok(reviewService.getReviewFromItem(itemId));
    }

    public ResponseEntity<MemberReviewResponseDto> getMemberReview(Long memberId) {
        return ResponseEntity.ok(reviewService.getReviewFromMember(memberId));
    }
}
