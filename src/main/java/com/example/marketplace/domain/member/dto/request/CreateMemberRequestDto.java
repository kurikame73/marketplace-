package com.example.marketplace.domain.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateMemberRequestDto {
    private String memberName;
    private String loginId;
    private String password;
    private String email;
}
