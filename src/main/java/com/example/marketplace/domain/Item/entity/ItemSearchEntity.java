package com.example.marketplace.domain.Item.entity;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "items")
public class ItemSearchEntity {

    private Long id;  // 상품 ID

    @Field(type = FieldType.Text, analyzer = "standard")
    private String itemName;

    @Field(type = FieldType.Keyword)
    private String categoryName;

    @Field(type = FieldType.Keyword)
    private String brand;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Integer)
    private Integer stockQuantity;

    @Field(type = FieldType.Double)
    private Double rating;

    @Field(type = FieldType.Integer)
    private Integer sales;

    @Field(type = FieldType.Boolean)
    private Boolean isOnPromotion;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String searchKeywords;

    @Field(type = FieldType.Keyword)
    private String status;

    @Field(type = FieldType.Keyword)
    private List<String> detailImages;
}
