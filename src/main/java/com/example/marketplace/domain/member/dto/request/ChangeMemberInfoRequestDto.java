package com.example.marketplace.domain.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor//test
public class ChangeMemberInfoRequestDto {
    private String loginId;
    private String email;
    private String phoneNumber;
    private String memberName;
}
