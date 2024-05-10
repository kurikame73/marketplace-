package com.example.marketplace.domain.member.repository;

import com.example.marketplace.domain.member.dto.response.GetMemberInfoResponseDto;
import com.example.marketplace.domain.member.entity.Member;
import com.example.marketplace.domain.member.entity.OauthId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByOauthId(OauthId oauthId);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByLoginId(String loginId);
    Optional<Member> findByName(String name);

    @Query("SELECT new com.example.marketplace.domain.member.dto.response.GetMemberInfoResponseDto" +
            "(m.id, m.loginId, m.email, m.name, m.profileImageUrl, m.nickname, m.address, m.gender, m.phoneNumber, m.age) " +
            "FROM Member m WHERE m.loginId = :loginId")
    GetMemberInfoResponseDto findMemberInfoByLoginId(@Param("loginId") String loginId);

}
