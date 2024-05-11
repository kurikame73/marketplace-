package com.example.marketplace.domain.Item.service;

import com.example.marketplace.domain.Item.dto.request.ItemFilterDto;
import com.example.marketplace.domain.Item.dto.response.ItemDto;
import com.example.marketplace.domain.Item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    // 메인페이지 아이템 프로모션타입별 조회

    // 아이템 디테일 조회는 아이템디테일 서비스에서

    // 상품 리뷰 노출은 리뷰 서비스에서

    // 쿠폰 받기는 쿠폰 서비스에서

    // 찜하기는 위시리스트 서비스에서

    // 아이템 필터 조회 (카테고리, 이름, 가격대, 브랜드, 프로모션타입, 상태, 페이징처리)
    public Page<ItemDto> findItems(ItemFilterDto itemFilterDto, Pageable pageable) {
        ItemFilter filter = new ItemFilter.ItemFilterBuilder()
                .categoryId(itemFilterDto.getCategoryId())
                .itemName(itemFilterDto.getItemName())
                .minPrice(itemFilterDto.getMinPrice())
                .maxPrice(itemFilterDto.getMaxPrice())
                .brand(itemFilterDto.getBrand())
                .promotionType(itemFilterDto.getPromotionType())
                .status(itemFilterDto.getStatus())
                .build();

        return itemRepository.filteringItem(filter, pageable);
    }
}
