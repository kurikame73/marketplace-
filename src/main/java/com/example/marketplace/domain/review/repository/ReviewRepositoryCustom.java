package com.example.marketplace.domain.review.repository;

import com.example.marketplace.domain.review.dto.response.ItemReviewResponseDto;
import com.example.marketplace.domain.review.dto.response.MemberReviewResponseDto;

public interface ReviewRepositoryCustom {
    ItemReviewResponseDto findItemReviewDtoByItemId(Long itemId);
    MemberReviewResponseDto findMemberReviewDtoByMemberId(Long memberId);
}
