package com.example.marketplace.domain.member.service;

import com.example.marketplace.domain.member.dto.request.CreateMemberRequestDto;
import com.example.marketplace.domain.member.dto.request.FindLoginIdRequestDto;
import com.example.marketplace.domain.member.entity.Member;
import com.example.marketplace.domain.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    // 일반 회원가입
    public void createMember(CreateMemberRequestDto dto) {
        // 예외 처리
        // loginId 검증, email 검증
        memberRepository.save(Member.createMember(dto));
    }

    public Boolean verifyEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    public Boolean verifyLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId).isPresent();
    }

    public Boolean verifyName(String name) {
        return memberRepository.findByName(name).isPresent();
    }

    public Boolean verifyPassword(PasswordCheckRequestDto dto) {
        return memberRepository.findByLoginId(dto.getLoginId).isPresent();
    }

    // 아이디 찾기
    public String findLoginId(FindLoginIdRequestDto dto) {
        if (verifyName(dto.getName()) && verifyEmail(dto.getEmail())) {
            return memberRepository.findByEmail(dto.getEmail())
                    .orElseThrow(() ->
                            new EntityNotFoundException
                                    ("회원을 찾을 수 없음 email: " + dto.getEmail())).getLoginId();
        } else {
            throw new IllegalArgumentException("이름 또는 이메일이 일치하지 않습니다.");
        }
    }

    // 비밀번호 변경
    public String changePassword(ChangePasswordRequestDto dto) {
        memberRepository.findByEmail(dto.getEmail)
                .orElseThrow(
                        () -> new EntityNotFoundException
                                ("비밀번호 변경에 실패함 회원을 찾을 수 없음 email: " + dto.getEmail())).changePassword(dto);
        return "비밀번호 변경 성공.";
    }

    // 개인정보 조회

    // 개인정보 수정

    // 추가정보 입력
}
