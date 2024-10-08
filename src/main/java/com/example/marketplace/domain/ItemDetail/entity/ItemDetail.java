package com.example.marketplace.domain.ItemDetail.entity;

import com.example.marketplace.domain.Item.entity.Item;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ItemDetail {

    @Id
    @GeneratedValue
    @Column(name = "item_detail_id")
    private Long id;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "item_id")
    private Item item;

    // TODO:  enum으로
    private String deliveryMethod;

    // 상품 정보 (예: 재료, 제조 방법 등)
    private String itemInfo;

    // 판매자 정보(예: 이름, 연락처 등)
    private String sellerInfo;

    // 포장 타입
    private String packagingType;

    // 안내 사항 (예: 주의 사항, 보관 방법 등)
    private String notes;

    // TODO:  OneToMany
    // 상품 상세 이미지 URL 리스트
    @JsonIgnore  // 직렬화 시 제외
    @ElementCollection
    @CollectionTable(name = "item_detail_images", joinColumns = @JoinColumn(name = "item_detail_id"))
    @Column(name = "image_url")
    private List<String> detailImages = new ArrayList<>();

    private String promotionImageUrl;

    // 추가 설명
    private String additionalDescription;
}
