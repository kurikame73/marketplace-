package com.example.marketplace.domain.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemReviewResponseDto {
    private Long id;
    private Long itemId;
    private Long memberId;
    private Integer rating;
    private String comment;
    private Integer helpful;
    private LocalDateTime createAt;
    private List<String> imageUrls;

    public ItemReviewResponseDto(Long id, Long itemId, Long memberId, Integer rating, String comment, Integer helpful) {
        this.id = id;
        this.itemId = itemId;
        this.memberId = memberId;
        this.rating = rating;
        this.comment = comment;
        this.helpful = helpful;
    }
}
