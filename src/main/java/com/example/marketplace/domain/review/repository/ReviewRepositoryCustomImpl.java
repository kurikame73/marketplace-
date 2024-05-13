package com.example.marketplace.domain.review.repository;

import com.example.marketplace.domain.review.dto.response.ItemReviewResponseDto;
import com.example.marketplace.domain.review.dto.response.MemberReviewResponseDto;
import com.example.marketplace.domain.review.entity.QReview;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public ItemReviewResponseDto findItemReviewDtoByItemId(Long itemId) {
        QReview review = QReview.review;
        ItemReviewResponseDto dto = queryFactory
                .select(Projections.constructor(
                        ItemReviewResponseDto.class,
                        review.id,
                        review.item.id,
                        review.member.id,
                        review.rating,
                        review.comment,
                        review.helpful))
                .from(review)
                .where(review.item.id.eq(itemId))
                .fetchOne();

        if (dto != null) {
            List<?> rawImageUrls = queryFactory
                    .select(review.imageUrls)
                    .from(review)
                    .where(review.id.eq(dto.getId()))
                    .fetch();

            List<String> imageUrls = rawImageUrls.stream()
                    .filter(obj -> obj instanceof String)
                    .map(obj -> (String) obj)
                    .toList();

            dto.setImageUrls(imageUrls);
        }
        return dto;
    }

    @Override
    public MemberReviewResponseDto findMemberReviewDtoByMemberId(Long memberId) {
        QReview review = QReview.review;
        MemberReviewResponseDto dto = queryFactory
                .select(Projections.constructor(
                        MemberReviewResponseDto.class,
                        review.id,
                        review.item.id,
                        review.member.id,
                        review.rating,
                        review.comment,
                        review.helpful))
                .from(review)
                .where(review.member.id.eq(memberId))
                .fetchOne();

        if (dto != null) {
            List<?> rawImageUrls = queryFactory
                    .select(review.imageUrls)
                    .from(review)
                    .where(review.id.eq(dto.getId()))
                    .fetch();

            List<String> imageUrls = rawImageUrls.stream()
                    .filter(obj -> obj instanceof String)
                    .map(obj -> (String) obj)
                    .toList();

            dto.setImageUrls(imageUrls);
        }
        return dto;

    }
}
