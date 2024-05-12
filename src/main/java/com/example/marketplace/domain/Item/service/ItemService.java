package com.example.marketplace.domain.Item.service;

import com.example.marketplace.domain.Item.dto.request.ItemFilterDto;
import com.example.marketplace.domain.Item.dto.response.ItemDto;
import com.example.marketplace.domain.Item.repository.ItemRepository;
import com.example.marketplace.listener.item.ItemRecommendedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;
    private final ApplicationEventPublisher eventPublisher;

    // 아이템 디테일 조회는 아이템디테일 서비스에서

    // 상품 리뷰 노출은 리뷰 서비스에서

    // 쿠폰 받기는 쿠폰 서비스에서

    // 찜하기는 위시리스트 서비스에서
    public Page<ItemDto> findItems(ItemFilterDto itemFilterDto, Pageable pageable) {
        ItemFilter filter = new ItemFilter.ItemFilterBuilder()
                .categoryId(itemFilterDto.getCategoryId())
                .categoryName(itemFilterDto.getCategoryName())
                .itemName(itemFilterDto.getItemName())
                .minPrice(itemFilterDto.getMinPrice())
                .maxPrice(itemFilterDto.getMaxPrice())
                .brand(itemFilterDto.getBrand())
                .promotionType(itemFilterDto.getPromotionType())
                .status(itemFilterDto.getStatus())
                .registeredDate(itemFilterDto.getRegisteredDate())
                .recommendation(itemFilterDto.getRecommendation())
                .discountRate(itemFilterDto.getDiscountRate())
                .sales(itemFilterDto.getSales())
                .sortByList(itemFilterDto.getSortByList())
                .build();

        return itemRepository.filteringItem(filter, pageable);
    }

    public void itemRecommendation(Long itemId, Long memberId) {
        eventPublisher.publishEvent(new ItemRecommendedEvent(itemId, memberId));
    }


    // TODO:  찜하기
    // TODO:  상품 리뷰
    // TODO:  아이템 디테일 조회
}
