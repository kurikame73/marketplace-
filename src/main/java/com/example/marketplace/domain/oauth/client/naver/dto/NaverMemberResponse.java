package com.example.marketplace.domain.oauth.client.naver.dto;

import com.example.marketplace.domain.member.entity.Member;
import com.example.marketplace.domain.member.entity.MemberType;
import com.example.marketplace.domain.member.entity.OauthId;
import com.example.marketplace.domain.oauth.OauthServerType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record NaverMemberResponse(
        String resultcode,
        String message,
        Response response
) {
    public Member toUserEntity() {
        return Member.builder()
                .oauthId(new OauthId(String.valueOf(response.id), OauthServerType.NAVER))
                .nickname(response.nickname)
                .profileImageUrl(response.profileImage)
                .email(response.email)
                .name(response.name)
                .memberType(MemberType.ROLE_GUEST)
                .build();
    }

    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Response(
            String id,
            String nickname,
            String name,
            String email,
            String gender,
            String age,
            String birthday,
            String profileImage,
            String birthyear,
            String mobile
    ) {
    }
}
