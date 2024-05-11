package com.example.marketplace.domain.Item.dto.response;

import com.example.marketplace.domain.Item.entity.PromotionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Long id;
    private String itemName;
    private Integer recommendation;
    private Integer sales;
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
    private String categoryName;
}
