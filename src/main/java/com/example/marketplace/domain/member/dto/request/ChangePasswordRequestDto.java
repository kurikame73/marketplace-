package com.example.marketplace.domain.member.dto.request;

import lombok.Getter;

@Getter
public class ChangePasswordRequestDto {
    private String loginId;
    private String newPassword;
    private String name;
    private String profileImageUrl;
    private String nickName;
}
