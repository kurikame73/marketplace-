package com.example.marketplace.domain.member.dto.request;

import lombok.Getter;

@Getter
public class ChangeMemberInfoRequestDto {
    private String loginId;
    private String email;
    private String phoneNumber;
    private String memberName;
}
