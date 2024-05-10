package com.example.marketplace.domain.review.entity;

import com.example.marketplace.domain.Item.entity.Item;
import com.example.marketplace.domain.member.entity.Member;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class Review {
    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne (fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    private Integer rating;

    private String comment;

    private Integer helpful;

    private LocalDateTime createAt;

    // 리뷰 이미지
    @ElementCollection
    @CollectionTable(name = "review_image", joinColumns = @JoinColumn(name = "review_id"))
    @Column(name = "image_url")
    private List<String> imageUrls = new ArrayList<>();
}
