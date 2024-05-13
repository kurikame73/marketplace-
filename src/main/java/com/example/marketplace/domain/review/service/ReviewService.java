package com.example.marketplace.domain.review.service;

import com.example.marketplace.domain.Item.entity.Item;
import com.example.marketplace.domain.Item.repository.ItemRepository;
import com.example.marketplace.domain.member.entity.Member;
import com.example.marketplace.domain.member.repository.MemberRepository;
import com.example.marketplace.domain.review.dto.request.CreateReviewRequestDto;
import com.example.marketplace.domain.review.dto.response.ItemReviewResponseDto;
import com.example.marketplace.domain.review.dto.response.MemberReviewResponseDto;
import com.example.marketplace.domain.review.entity.Review;
import com.example.marketplace.domain.review.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    public void createReview(CreateReviewRequestDto dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없음 memberId: " + dto.getMemberId()));
        Item item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없음 itemId: " + dto.getItemId()));
        reviewRepository.save(Review
                .createReview(dto, member, item));
    }

    // 아이템 리뷰 조회
    public ItemReviewResponseDto getReviewFromItem(Long itemId) {
        return reviewRepository.findItemReviewDtoByItemId(itemId);
    }

    // 회원 리뷰 조회
    public MemberReviewResponseDto getReviewFromMember(Long memberId) {
        return reviewRepository.findMemberReviewDtoByMemberId(memberId);
    }
}
