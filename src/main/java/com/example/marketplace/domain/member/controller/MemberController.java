package com.example.marketplace.domain.member.controller;

import com.example.marketplace.domain.member.dto.request.*;
import com.example.marketplace.domain.member.dto.response.GetMemberInfoResponseDto;
import com.example.marketplace.domain.member.repository.MemberRepository;
import com.example.marketplace.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    // 로그인 아이디 중복 검사
    @GetMapping("/check-loginid")
    public ResponseEntity<String> checkLoginIdAvailability(@RequestParam String loginId) {
        memberService.verifyLoginId(loginId);
        return ResponseEntity.ok("로그인 아이디 사용할 수 있음 loginId: " + loginId);
    }

    // 이메일 중복 검사
    @GetMapping("/check-email")
    public ResponseEntity<String> checkEmailAvailability(@RequestParam String email) {
        memberService.verifyEmail(email);
        return ResponseEntity.ok("이메일을 사용할 수 있음 email: " + email);
    }

    // 로그인 아이디, 이메일 동시 검사
    @GetMapping("/check-loginid-email")
    public ResponseEntity<String> checkEmailAndLoginId(@RequestBody CheckLoginIdAndEmailRequestDto dto) {
        memberService.verifyEmail(dto.getEmail());
        memberService.verifyLoginId(dto.getLoginId());
        return ResponseEntity.ok("이메일, 로그인아이디가 일치함");
    }

    @GetMapping("/validate-password")
    public ResponseEntity<String> validatePassword(PasswordCheckRequestDto dto) {
        memberService.verifyPassword(dto);
        return ResponseEntity.ok("비밀번호가 일치함");
    }

    // 아이디 찾기
    @GetMapping("/find-loginid")
    public ResponseEntity<String> findLoginId(@RequestBody FindLoginIdRequestDto dto) {
        return ResponseEntity.ok("회원 아이디 찾음 loginId: " + memberService.findLoginId(dto));
    }

    // 회원 정보 변경
    @PostMapping("/change-member-info")
    public ResponseEntity<String> changeMemberInfo(@RequestBody ChangeMemberInfoRequestDto dto) {
        memberService.changeMemberInfo(dto);
        return ResponseEntity.ok("회원 정보가 성공적으로 변경됨 loginId: " + dto.getLoginId());
    }

    // 회원 정보 조회
    @GetMapping("/get-member-info")
    public ResponseEntity<GetMemberInfoResponseDto> getMemberInfo(@RequestBody GetMemberInfoRequestDto dto) {
        return ResponseEntity.ok(memberService.getMemberInfo(dto)) ;
    }

    @PostMapping("/add-information-member")
    public ResponseEntity<String> addMemberInformation(@RequestBody AddInformationMemberRequestDto dto) {
        memberService.addInformationMember(dto);
        return ResponseEntity.ok("회원 정보 추가가 완료됨 loginId: " + dto.getLoginId());
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequestDto dto) {
        memberService.changePassword(dto);
        return ResponseEntity.ok("비밀번호가 성공적으로 변경이 완료됨");
    }

    @PostMapping("/create-member")
    public ResponseEntity<String> createMember(CreateMemberRequestDto dto) {
        memberService.createMember(dto);
        return ResponseEntity.ok("회원이 성공적으로 생성됨");
    }

    // TODO:  주문 내역 조회
    // TODO:  주문 내역 상세 조회
    // TODO:  쿠폰 조회
    // TODO:  개인정보, 찜한것, 쿠폰 정보 노출
}
