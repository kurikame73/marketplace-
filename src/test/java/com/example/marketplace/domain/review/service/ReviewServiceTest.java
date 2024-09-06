package com.example.marketplace.domain.review.service;

import com.example.marketplace.domain.Item.entity.Item;
import com.example.marketplace.domain.Item.repository.ItemRepository;
import com.example.marketplace.domain.config.TestConfig;
import com.example.marketplace.domain.member.entity.Member;
import com.example.marketplace.domain.member.repository.MemberRepository;
import com.example.marketplace.domain.review.dto.request.CreateReviewRequestDto;
import com.example.marketplace.domain.review.dto.response.ItemReviewResponseDto;
import com.example.marketplace.domain.review.dto.response.MemberReviewResponseDto;
import com.example.marketplace.domain.review.entity.Review;
import com.example.marketplace.domain.review.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TestConfig.class)
@Transactional
@ActiveProfiles("test")
@Slf4j
class ReviewServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewService reviewService;

    private Long memberId1, itemId1;

    @BeforeEach
    void setUp() {
        // Create member
        Member member = new Member("John Doe", "johndoe@example.com");
        member = memberRepository.save(member);
        memberId1 = member.getId(); // Save member ID for test reference

        // Create item
        Item item = new Item("Awesome Laptop", 1500);
        item = itemRepository.save(item);
        itemId1 = item.getId(); // Save item ID for test reference

        // Create a review
        // Given
        CreateReviewRequestDto dto = new CreateReviewRequestDto(itemId1, memberId1,"Great product!", List.of("a","b"));

        // When
        reviewService.createReview(dto);
    }

    @Test
    void createReview() {
        // Given
        CreateReviewRequestDto dto = new CreateReviewRequestDto(itemId1, memberId1,"Great product!", List.of("a","b"));

        // When
        reviewService.createReview(dto);

        // Then
        assertFalse(reviewRepository.findAll().isEmpty());
    }

    @Test
    void getReviewFromItem() {
        // given
        // itemId1

        // when
        ItemReviewResponseDto response = reviewService.getReviewFromItem(itemId1);

        // then
        assertNotNull(response);
        assertEquals(itemId1, response.getItemId());

        log.info("response!!! = {}", response);
    }

    @Test
    void getReviewFromMember() {
        // Given
        // memberId1

        // When
        log.info("memberId1!!! = {}", memberId1);
        MemberReviewResponseDto response = reviewService.getReviewFromMember(memberId1);
        log.info("responseMem!!! = {}", response);


        // Then
        assertNotNull(response);
        assertEquals(memberId1, response.getMemberId());
    }
}