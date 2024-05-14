package com.example.marketplace.domain.inquiry.dto.response;

import com.example.marketplace.domain.inquiry.entity.InquiryType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetInquiryResponseDto {
    private Long id;
    private Long memberId;
    private InquiryType inquiryType;
    private String title;
    private String content;
    private List<String> imageUrls;

    public GetInquiryResponseDto(Long id, Long memberId, InquiryType inquiryType, String title, String content) {
        this.id = id;
        this.memberId = memberId;
        this.inquiryType = inquiryType;
        this.title = title;
        this.content = content;
    }
}
