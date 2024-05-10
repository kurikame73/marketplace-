package com.example.marketplace.domain.member.dto.request;

import lombok.Getter;

@Getter
public class PasswordCheckRequestDto {
    private String newPassword;
    private String loginId;
}
