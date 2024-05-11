package com.example.marketplace.domain.Item.service;

import com.example.marketplace.domain.Item.entity.ItemStatus;
import com.example.marketplace.domain.Item.entity.PromotionType;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDateTime registeredDate;
    private Integer recommendation;
    private Integer sales;
    private Integer discountRate;
    private List<Sort.Order> sortByList;

    public static class ItemFilterBuilder {
        private Long categoryId;
        private String categoryName;
        private String itemName;
        private Integer minPrice;
        private Integer maxPrice;
        private String brand;
        private PromotionType promotionType;
        private ItemStatus status;
        private LocalDateTime registeredDate;
        private Integer recommendation;
        private Integer sales;
        private Integer discountRate;
        private List<Sort.Order> sortByList;

        public ItemFilterBuilder categoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public ItemFilterBuilder discountRate(Integer discountRate) {
            this.discountRate = discountRate;
            return this;
        }

        public ItemFilterBuilder sales(Integer sales) {
            this.sales = sales;
            return this;
        }

        public ItemFilterBuilder registeredDate(LocalDateTime registeredDate) {
            this.registeredDate = registeredDate;
            return this;
        }

        public ItemFilterBuilder sortByList(List<Sort.Order> sortByList) {
            this.sortByList = sortByList;
            return this;
        }

        public ItemFilterBuilder categoryName(String categoryName) {
            this.categoryName = categoryName;
            return this;
        }

        public ItemFilterBuilder recommendation(Integer recommendation) {
            this.recommendation = recommendation;
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
            filter.registeredDate = this.registeredDate;
            filter.sortByList = this.sortByList;
            filter.recommendation = this.recommendation;
            filter.discountRate = this.discountRate;
            filter.sales = this.sales;
            return filter;
        }
    }
}
