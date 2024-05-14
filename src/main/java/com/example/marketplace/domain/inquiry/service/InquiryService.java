package com.example.marketplace.domain.inquiry.service;

import com.example.marketplace.domain.inquiry.dto.request.CreateInquiryRequestDto;
import com.example.marketplace.domain.inquiry.entity.Inquiry;
import com.example.marketplace.domain.inquiry.repository.InquiryRepository;
import com.example.marketplace.domain.member.entity.Member;
import com.example.marketplace.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class InquiryService {
    private final MemberRepository memberRepository;
    private final InquiryRepository inquiryRepository;

    public void createInquiry(CreateInquiryRequestDto dto) {
        Member member = memberRepository.findById(dto.getMemberId()).orElseThrow();
        inquiryRepository.save(Inquiry.createInquiry(dto, member));
    }


}
