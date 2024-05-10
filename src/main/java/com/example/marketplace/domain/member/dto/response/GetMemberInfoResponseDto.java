package com.example.marketplace.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetMemberInfoResponseDto {
    private Long id;
    private String loginId;
    private String email;
    private String name;
    private String profileImageUrl;
    private String nickname;
    private String address;
    private String gender;
    private String phoneNumber;
    private Long age;
// 추가적인 필드가 필요하면
}
