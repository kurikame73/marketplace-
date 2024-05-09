package com.example.marketplace.domain.oauth.controller;

import com.example.marketplace.domain.jwt.TokenProvider;
import com.example.marketplace.domain.member.entity.Member;
import com.example.marketplace.domain.member.entity.MemberType;
import com.example.marketplace.domain.oauth.OauthServerType;
import com.example.marketplace.domain.oauth.dto.LoginDto;
import com.example.marketplace.domain.oauth.service.OauthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/oauth")
@RestController
@Slf4j
public class OauthController {
    private final OauthService oauthService;
    private final TokenProvider tokenProvider;

    @SneakyThrows
    @GetMapping("/{oauthServerType}")
    ResponseEntity<Void> redirectAuthCodeRequestUrl(
            @PathVariable OauthServerType oauthServerType,
            HttpServletResponse response) {
        String redirectUrl = oauthService.getAuthCodeRequestUrl(oauthServerType);
        response.sendRedirect(redirectUrl);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login/{oauthServerType}")
    LoginDto login(
            @PathVariable OauthServerType oauthServerType,
            @RequestParam String code) {
        Member member = oauthService.login(oauthServerType, code);
        String token = tokenProvider.
                createTokenOauth(member);
        boolean isMember = member.getMemberType() == MemberType.ROLE_USER;

        LoginDto dto = new LoginDto();
        dto.setToken(token);
        dto.setIsUser(isMember);

        return dto;
    }
}
