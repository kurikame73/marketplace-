package com.example.marketplace.domain.inquiry.entity;

import com.example.marketplace.domain.inquiry.dto.request.CreateInquiryRequestDto;
import com.example.marketplace.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private InquiryType inquiryType;

    private String title;
    private String content;

    @ElementCollection
    @CollectionTable(name = "inquiry_images", joinColumns = @JoinColumn(name = "inquiry_id"))
    @Column(name = "image_url")
    private List<String> imageUrls = new ArrayList<>();

    //작성일
    private LocalDate registerDate;

    //답변상태
    @Enumerated(EnumType.STRING)
    private InquiryStatus status;

    public static Inquiry createInquiry(CreateInquiryRequestDto dto, Member member) {
        return Inquiry.builder()
                .member(member)
                .inquiryType(dto.getInquiryType())
                .title(dto.getTitle())
                .content(dto.getContent())
                .registerDate(LocalDate.now())
                .status(InquiryStatus.B)
                .imageUrls(dto.getImageUrls())
                .build();
    }
}
