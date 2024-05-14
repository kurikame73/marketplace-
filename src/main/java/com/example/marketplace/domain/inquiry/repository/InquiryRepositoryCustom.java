package com.example.marketplace.domain.inquiry.repository;

import com.example.marketplace.domain.inquiry.dto.response.GetInquiryResponseDto;

import java.util.List;

public interface InquiryRepositoryCustom {
    GetInquiryResponseDto getInquiryAllByMemberId(Long memberId);
}
