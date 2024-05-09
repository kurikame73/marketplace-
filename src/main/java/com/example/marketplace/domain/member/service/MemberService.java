package com.example.marketplace.domain.member.service;

import com.example.marketplace.domain.member.dto.request.CreateMemberRequestDto;
import com.example.marketplace.domain.member.dto.request.FindLoginIdRequestDto;
import com.example.marketplace.domain.member.entity.Member;
import com.example.marketplace.domain.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 일반 회원가입
    public void createMember(CreateMemberRequestDto dto) {
        // 예외 처리
        // loginId 검증, email 검증
        memberRepository.save(Member.createMember(dto));
    }

    // 아이디 찾기
    public String findLoginId(FindLoginIdRequestDto dto) {
        verifyEmail(dto.getEmail());
        verifyName(dto.getName());
        return memberRepository.findByEmail(dto.getEmail())
                .map(Member::getEmail).orElse(null);
    }

    // 비밀번호 변경
    public void changePassword(ChangePasswordRequestDto dto) {
        memberRepository.findByLoginId(dto.getLoginId)
                .orElseThrow(() -> new EntityNotFoundException
                        ("회원을 찾을 수 없음 loginId: " + dto.getLoginId))
                .changePassword(dto);
    }

    // 개인정보 조회
    public GetMemberInfoResponseDto getMemberInfo(GetMemberInfoRequestDto dto) {
        return memberRepository.findMemberInfoByLoginId(dto.getLoginId);
    }

    // 개인정보 수정
    public void changeMemberInfo(ChangeMemberInfoRequestDto dto) {
        // 검증
        memberRepository.findByLoginId(dto.getLoginId).orElse().changeMemberInfo;
    }

    // 추가정보 입력
    public void addInformationMember(AddInformationMemberRequestDto dto) {
        memberRepository.findByLoginId(dto.getLoginId)
                .orElseThrow(() -> new EntityNotFoundException
                        ("회원을 찾을 수 없음 LoginId: " + dto.getLoginId));
    }

    // 비밀번호 검증 -> 비밀번호 변경
    // 비밀번호 검증
    public void verifyPassword(PasswordCheckRequestDto dto) {
        passwordEncoder.matches(
                dto.getNewPassword,
                memberRepository.findByLoginId(dto.getLoginId)
                        .orElseThrow("회원을 찾을 수 없음 loginId: " + dto.getLoginId)
                        .getPassword());
    }

    public void verifyName(String name) {
        memberRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없음 name: " + name));
    }

    public void verifyEmail(String email) {
        memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없음 email: " + email));
    }

    public void verifyLoginId(String loginId) {
        memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없음 loginId: " + loginId));
    }
}
