package com.example.marketplace.domain.Item.dto.response;

import com.example.marketplace.domain.Item.entity.PromotionType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Long id;
    private String itemName;
    private LocalDateTime promotionStart;
    private LocalDateTime promotionEnd;
    private LocalDateTime registeredDate;
    private Integer stockQuantity;
    private Integer itemPrice;
    private Integer discountRate;
    private String brand;
    private PromotionType promotionType;
    private Long categoryId; // Category의 id
    private Integer reviewCount; // Review의 개수
}
