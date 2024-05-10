package com.example.marketplace.domain.member.service;

import com.example.marketplace.domain.member.dto.request.*;
import com.example.marketplace.domain.member.dto.response.GetMemberInfoResponseDto;
import com.example.marketplace.domain.member.entity.Member;
import com.example.marketplace.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository, passwordEncoder);
    }

    @Test
    void createMember() {
        // given
        CreateMemberRequestDto requestDto = new CreateMemberRequestDto("testName", "testLoginId", "testPassword", "testEmail@test.com");

        // when
        memberService.createMember(requestDto);

        // then
        Optional<Member> savedMember = memberRepository.findByLoginId("testLoginId");
        assertTrue(savedMember.isPresent());
        assertEquals("testLoginId", savedMember.get().getLoginId());
        assertEquals("testName", savedMember.get().getName());
        assertEquals("testEmail@test.com", savedMember.get().getEmail());
        assertTrue(passwordEncoder.matches("testPassword", savedMember.get().getPassword()));
    }

    @Test
    void findLoginId() {
        // given
        String email = "test@example.com";
        String name = "testName";
        Member member = Member.builder()
                .loginId("testLoginId")
                .email(email)
                .name(name)
                .build();
        memberRepository.save(member);

        FindLoginIdRequestDto requestDto = new FindLoginIdRequestDto(name, email);

        // when
        String foundLoginId = memberService.findLoginId(requestDto);

        // then
        assertEquals("testLoginId", foundLoginId);
    }

    @Test
    void changePassword() {
        // given
        String loginId = "testLoginId";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        String name = "name";
        String url = "abc.com";
        String nickName = "abcd";
        Member member = Member.builder()
                .loginId(loginId)
                .password(passwordEncoder.encode(oldPassword))
                .build();
        memberRepository.save(member);

        ChangePasswordRequestDto requestDto = new ChangePasswordRequestDto(loginId, newPassword, name, url, nickName);

        // when
        memberService.changePassword(requestDto);

        // then
        Optional<Member> updatedMember = memberRepository.findByLoginId(loginId);
        assertTrue(updatedMember.isPresent());
        assertTrue(passwordEncoder.matches(newPassword, updatedMember.get().getPassword()));

    }

    @Test
    void getMemberInfo() {
        // given
        String loginId = "testLoginId";
        Member member = Member.builder()
                .loginId(loginId)
                .email("test@example.com")
                .name("testName")
                .profileImageUrl("http://example.com/profile.jpg")
                .nickname("testNickname")
                .address("testAddress")
                .gender("MALE")
                .phoneNumber("01012345678")
                .age(30L)
                .build();
        memberRepository.save(member);

        GetMemberInfoRequestDto requestDto = new GetMemberInfoRequestDto(loginId);

        // when
        GetMemberInfoResponseDto responseDto = memberService.getMemberInfo(requestDto);

        // then
        assertNotNull(responseDto);
        assertEquals(member.getId(), responseDto.getId());
        assertEquals(member.getLoginId(), responseDto.getLoginId());
        assertEquals(member.getEmail(), responseDto.getEmail());
        assertEquals(member.getName(), responseDto.getName());
        assertEquals(member.getProfileImageUrl(), responseDto.getProfileImageUrl());
        assertEquals(member.getNickname(), responseDto.getNickname());
        assertEquals(member.getAddress(), responseDto.getAddress());
        assertEquals(member.getGender(), responseDto.getGender());
        assertEquals(member.getPhoneNumber(), responseDto.getPhoneNumber());
        assertEquals(member.getAge(), responseDto.getAge());

    }

    @Test
    void changeMemberInfo() {
        // given
        String loginId = "testLoginId";
        String email = "new@example.com";
        String name = "newName";
        String phoneNumber = "01098765432";
        Member member = Member.builder()
                .loginId(loginId)
                .email("old@example.com")
                .name("oldName")
                .phoneNumber("01012345678")
                .build();
        memberRepository.save(member);

        ChangeMemberInfoRequestDto requestDto = new ChangeMemberInfoRequestDto(
                loginId, email, phoneNumber, name
        );

        // when
        memberService.changeMemberInfo(requestDto);

        // then
        Optional<Member> updatedMember = memberRepository.findByLoginId(loginId);
        assertTrue(updatedMember.isPresent());
        assertEquals("new@example.com", updatedMember.get().getEmail());
        assertEquals("newName", updatedMember.get().getName());
        assertEquals("01098765432", updatedMember.get().getPhoneNumber());

    }

    @Test
    void addInformationMember() {
        // given
        String loginId = "testLoginId";
        String email = "test@example.com";
        String name = "testName";
        String phoneNumber = "01012345678";
        Member member = Member.builder()
                .loginId(loginId)
                .build();
        memberRepository.save(member);

        AddInformationMemberRequestDto requestDto = new AddInformationMemberRequestDto(
                loginId, email, phoneNumber, name
        );

        // when
        memberService.addInformationMember(requestDto);

        // then
        Optional<Member> updatedMember = memberRepository.findByLoginId(loginId);
        assertTrue(updatedMember.isPresent());
        assertEquals("test@example.com", updatedMember.get().getEmail());
        assertEquals("testName", updatedMember.get().getName());
        assertEquals("01012345678", updatedMember.get().getPhoneNumber());

    }

    @Test
    void verifyPassword() {
        // given
        String loginId = "testLoginId";
        String password = "testPassword";
        Member member = Member.builder()
                .loginId(loginId)
                .password(passwordEncoder.encode(password))
                .build();
        memberRepository.save(member);

        PasswordCheckRequestDto requestDto = new PasswordCheckRequestDto(password, loginId);

        // when, then
        assertDoesNotThrow(() -> memberService.verifyPassword(requestDto));
    }

    @Test
    void verifyName() {
        // given
        String name = "testName";
        Member member = Member.builder()
                .name(name)
                .build();
        memberRepository.save(member);

        // when, then
        assertDoesNotThrow(() -> memberService.verifyName(name));
    }

    @Test
    void verifyEmail() {
        // given
        String email = "test@example.com";
        Member member = Member.builder()
                .email(email)
                .build();
        memberRepository.save(member);

        // when, then
        assertDoesNotThrow(() -> memberService.verifyEmail(email));
    }

    @Test
    void verifyLoginId() {
        // given
        String loginId = "testLoginId";
        Member member = Member.builder()
                .loginId(loginId)
                .build();
        memberRepository.save(member);

        // when, then
        assertDoesNotThrow(() -> memberService.verifyLoginId(loginId));
    }
    // TODO: 카테고리, 아이템 순서로
}