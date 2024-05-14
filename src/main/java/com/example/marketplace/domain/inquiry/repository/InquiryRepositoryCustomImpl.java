package com.example.marketplace.domain.inquiry.repository;

import com.example.marketplace.domain.inquiry.dto.response.GetInquiryResponseDto;
import com.example.marketplace.domain.inquiry.entity.QInquiry;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class InquiryRepositoryCustomImpl implements InquiryRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public GetInquiryResponseDto getInquiryAllByMemberId(Long memberId) {
        QInquiry inquiry = QInquiry.inquiry;
        GetInquiryResponseDto dto = queryFactory
                .select(Projections.constructor(
                        GetInquiryResponseDto.class,
                        inquiry.id,
                        inquiry.member.id,
                        inquiry.inquiryType,
                        inquiry.content))
                .from(inquiry)
                .where(inquiry.member.id.eq(memberId))
                .fetchOne();

        if (dto != null) {
            List<?> rawImageUrls = queryFactory
                    .select(inquiry.imageUrls)
                    .from(inquiry)
                    .where(inquiry.id.eq(dto.getId()))
                    .fetch();

            List<String> imageUrls = rawImageUrls.stream()
                    .filter(obj -> obj instanceof String)
                    .map(obj -> (String) obj)
                    .collect(Collectors.toList());

            dto.setImageUrls(imageUrls);
        }
        return dto;
    }
}
