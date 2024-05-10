package com.example.marketplace.domain.member.dto.request;

import lombok.Getter;

@Getter
public class CheckLoginIdAndEmailRequestDto {
    private String loginId;
    private String email;
}
