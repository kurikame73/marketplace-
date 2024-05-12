package com.example.marketplace.domain.ItemDetail.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DetailImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_detail_id")
    private ItemDetail itemDetail;
}
