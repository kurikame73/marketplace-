package com.example.marketplace.domain.Item.repository;

import com.example.marketplace.domain.Item.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    Optional<Recommendation> findByMemberId(Long memberId);
}
