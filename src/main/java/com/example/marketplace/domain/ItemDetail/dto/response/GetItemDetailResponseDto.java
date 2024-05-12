package com.example.marketplace.domain.ItemDetail.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetItemDetailResponseDto {
    private Long id;
    private Long itemId;
    private String itemInfo;
    private String sellerInfo;
    private String packagingType;
    private String notes;
    private List<String> detailImages;
    private String promotionImageUrl;
    private String additionalDescription;

    public GetItemDetailResponseDto(Long id, Long itemId, String itemInfo, String sellerInfo, String packagingType, String notes, String promotionImageUrl, String additionalDescription) {
        this.id = id;
        this.itemId = itemId;
        this.itemInfo = itemInfo;
        this.sellerInfo = sellerInfo;
        this.packagingType = packagingType;
        this.notes = notes;
        this.promotionImageUrl = promotionImageUrl;
        this.additionalDescription = additionalDescription;
    }
}
