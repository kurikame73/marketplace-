package com.example.marketplace.domain.Item.dto.request;

import com.example.marketplace.domain.Item.entity.ItemStatus;
import com.example.marketplace.domain.Item.entity.PromotionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemFilterDto {
    private Long categoryId;
    private String categoryName;
    private String itemName;
    private Integer minPrice;
    private Integer maxPrice;
    private String brand;
    private PromotionType promotionType;
    private ItemStatus status;
}
