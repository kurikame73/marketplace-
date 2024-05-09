package com.example.marketplace.domain.oauth.client.kakao.client;

import com.example.marketplace.domain.member.entity.Member;
import com.example.marketplace.domain.oauth.OauthServerType;
import com.example.marketplace.domain.oauth.client.OauthMemberClient;
import com.example.marketplace.domain.oauth.client.kakao.dto.KakaoMemberResponse;
import com.example.marketplace.domain.oauth.client.kakao.dto.KakaoOauthConfig;
import com.example.marketplace.domain.oauth.client.kakao.dto.KakaoToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoMemberClient implements OauthMemberClient {
    private final KakaoApiClient kakaoApiClient;
    private final KakaoOauthConfig kakaoOauthConfig;

    @Override
    public OauthServerType supportServer() {
        return OauthServerType.KAKAO;
    }

    @Override
    public Member fetchCustom(String authCode) {
        KakaoToken tokenInfo = kakaoApiClient.fetchToken(tokenRequestParams(authCode));
        KakaoMemberResponse kakaoMemberResponse = kakaoApiClient.
                fetchMember("Bearer " + tokenInfo.accessToken());
        return kakaoMemberResponse.toUserEntity();
    }

    private MultiValueMap<String, String> tokenRequestParams(String authCode) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoOauthConfig.clientId());
        params.add("redirect_uri", kakaoOauthConfig.redirectUri());
        params.add("code", authCode);
        params.add("client_secret", kakaoOauthConfig.clientSecret());
        return params;
    }
}
