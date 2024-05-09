package com.example.marketplace.domain.member.repository;

import com.example.marketplace.domain.member.entity.Member;
import com.example.marketplace.domain.member.entity.OauthId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByOauthId(OauthId oauthId);
    Optional<Member> findByEmail(String email);
    Optional<Member> findById(Long id);
    Optional<Member> findByLoginId(String loginId);
    Optional<Member> findByName(String name);
}
