package com.example.marketplace.domain.oauth.service;

import com.example.marketplace.domain.member.entity.Member;
import com.example.marketplace.domain.member.repository.MemberRepository;
import com.example.marketplace.domain.oauth.AuthCodeRequestUrlProviderComposite;
import com.example.marketplace.domain.oauth.OauthServerType;
import com.example.marketplace.domain.oauth.client.OauthMemberClientComposite;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OauthService {
    private final AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;
    private final OauthMemberClientComposite oauthMemberClientComposite;
    private final MemberRepository memberRepository;

    public String getAuthCodeRequestUrl(OauthServerType oauthServerType) {
        return authCodeRequestUrlProviderComposite.provide(oauthServerType);
    }

    public Member login(OauthServerType oauthServerType, String authCode) {
        Member member = oauthMemberClientComposite.fetchCustom(oauthServerType, authCode);
        return memberRepository.findByOauthId(member.getOauthId())
                .orElseGet(() -> memberRepository.save(member));}
}
