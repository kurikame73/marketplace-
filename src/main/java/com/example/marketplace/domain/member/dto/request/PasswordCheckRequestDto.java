package com.example.marketplace.domain.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor//test
public class PasswordCheckRequestDto {
    private String newPassword;
    private String loginId;
}
