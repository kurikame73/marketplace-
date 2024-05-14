package com.example.marketplace.domain.inquiry.dto.request;

import com.example.marketplace.domain.inquiry.entity.InquiryType;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateInquiryRequestDto {
    private Long memberId;

    private InquiryType inquiryType;

    private String title;

    private String content;

    private List<String> imageUrls;
}
