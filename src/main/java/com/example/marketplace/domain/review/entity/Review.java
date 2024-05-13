package com.example.marketplace.domain.review.entity;

import com.example.marketplace.domain.Item.entity.Item;
import com.example.marketplace.domain.member.entity.Member;
import com.example.marketplace.domain.review.dto.request.CreateReviewRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public static Review createReview(CreateReviewRequestDto dto, Member member, Item item){
        return Review.builder()
                .member(member)
                .item(item)
                .comment(dto.getComment())
                .imageUrls(dto.getImageUrls())
                .createAt(LocalDateTime.now())
                .build();
    }

    public Review(Member member, Item item, Integer rating, String comment, Integer helpful) {
        this.member = member;
        this.item = item;
        this.rating = rating;
        this.comment = comment;
        this.helpful = helpful;
    }
}
