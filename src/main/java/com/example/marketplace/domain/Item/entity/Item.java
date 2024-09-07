package com.example.marketplace.domain.Item.entity;

import com.example.marketplace.domain.ItemDetail.entity.ItemDetail;
import com.example.marketplace.domain.category.entity.Category;
import com.example.marketplace.domain.review.entity.Review;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Slf4j
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private ItemStatus status;

    @Enumerated(EnumType.STRING)
    private PromotionType promotionType;

    @JsonManagedReference
    @OneToOne(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private ItemDetail itemDetail;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties({"parent", "children", "items"})
    private Category category;

    @OneToMany(mappedBy = "item")
    private List<Review> reviews;

    // 추천 수
//    private Integer recommendation;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private Set<Recommendation> recommendations;

    // 판매량
    private Integer sales;

    public Item(String itemName, Integer itemPrice) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public void decreaseQuantity(Integer amount) {
        if (this.stockQuantity < amount) {
            throw new IllegalStateException("재고 부족.");
        }
        this.stockQuantity -= amount;
    }
}
