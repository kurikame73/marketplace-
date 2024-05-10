package com.example.marketplace.domain.Item.entity;

import com.example.marketplace.domain.category.entity.Category;
import com.example.marketplace.domain.review.entity.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Slf4j
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String itemName;

    private LocalDateTime promotionStart;

    private LocalDateTime promotionEnd;

    private LocalDateTime registeredDate;

    private Integer stockQuantity;

    private Integer itemPrice;

    private Integer discountRate;

    private String brand;

    @Enumerated(EnumType.STRING)
    private PromotionType promotionType = PromotionType.NONE;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "item")
    private List<Review> reviews = new ArrayList<>();
}
