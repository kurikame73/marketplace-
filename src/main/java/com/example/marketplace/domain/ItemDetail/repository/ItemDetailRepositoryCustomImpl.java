package com.example.marketplace.domain.ItemDetail.repository;

import com.example.marketplace.domain.ItemDetail.dto.response.GetItemDetailResponseDto;
import com.example.marketplace.domain.ItemDetail.entity.QItemDetail;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class ItemDetailRepositoryCustomImpl implements ItemDetailRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public GetItemDetailResponseDto findItemDetailByItemId(Long itemId) {
        QItemDetail itemDetail = QItemDetail.itemDetail;
        GetItemDetailResponseDto dto = queryFactory
                .select(Projections.constructor(
                        GetItemDetailResponseDto.class,
                        itemDetail.id,
                        itemDetail.item.id,
                        itemDetail.itemInfo,
                        itemDetail.sellerInfo,
                        itemDetail.packagingType,
                        itemDetail.notes,
                        itemDetail.promotionImageUrl,
                        itemDetail.additionalDescription))
                .from(itemDetail)
                .where(itemDetail.item.id.eq(itemId))
                .fetchOne();

        if (dto != null) {
            List<?> rawImageUrls = queryFactory
                    .select(itemDetail.detailImages)
                    .from(itemDetail)
                    .where(itemDetail.id.eq(dto.getId()))
                    .fetch();

            List<String> imageUrls = rawImageUrls.stream()
                    .filter(obj -> obj instanceof String)
                    .map(obj -> (String) obj)
                    .collect(Collectors.toList());

            dto.setDetailImages(imageUrls);
        }
        return dto;
    }
}
