package com.example.marketplace.domain.Item.service;

import com.example.marketplace.domain.Item.entity.ItemStatus;
import com.example.marketplace.domain.Item.entity.PromotionType;
import lombok.Data;

@Data
public class ItemFilter {
    private Long categoryId;
    private String categoryName;
    private String itemName;
    private Integer minPrice;
    private Integer maxPrice;
    private String brand;
    private PromotionType promotionType;
    private ItemStatus status;

    public static class ItemFilterBuilder {
        private Long categoryId;
        private String categoryName;
        private String itemName;
        private Integer minPrice;
        private Integer maxPrice;
        private String brand;
        private PromotionType promotionType;
        private ItemStatus status;

        public ItemFilterBuilder categoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public ItemFilterBuilder categoryName(String categoryName) {
            this.categoryName = categoryName;
            return this;
        }

        public ItemFilterBuilder itemName(String itemName) {
            this.itemName = itemName;
            return this;
        }

        public ItemFilterBuilder minPrice(Integer minPrice) {
            this.minPrice = minPrice;
            return this;
        }

        public ItemFilterBuilder maxPrice(Integer maxPrice) {
            this.maxPrice = maxPrice;
            return this;
        }

        public ItemFilterBuilder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public ItemFilterBuilder promotionType(PromotionType promotionType) {
            this.promotionType = promotionType;
            return this;
        }

        public ItemFilterBuilder status(ItemStatus status) {
            this.status = status;
            return this;
        }

        public ItemFilter build() {
            ItemFilter filter = new ItemFilter();
            filter.categoryId = this.categoryId;
            filter.categoryName = this.categoryName;
            filter.itemName = this.itemName;
            filter.minPrice = this.minPrice;
            filter.maxPrice = this.maxPrice;
            filter.brand = this.brand;
            filter.promotionType = this.promotionType;
            filter.status = this.status;
            return filter;
        }
    }
}
