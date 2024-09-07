package com.example.marketplace.domain.Item.service;

import com.example.marketplace.domain.Item.dto.request.ItemFilterDto;
import com.example.marketplace.domain.Item.dto.response.ItemDto;
import com.example.marketplace.domain.Item.entity.Item;
import com.example.marketplace.domain.Item.entity.ItemSearchEntity;
import com.example.marketplace.domain.Item.entity.PromotionType;
import com.example.marketplace.domain.Item.repository.ItemRepository;
import com.example.marketplace.listener.item.ItemRecommendedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {
    private final ItemRepository itemRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;


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

    @Transactional
    public void itemRecommendation(Long itemId, Long memberId) {
        eventPublisher.publishEvent(new ItemRecommendedEvent(itemId, memberId));
    }

    @Transactional
    public void decreaseItemQuantity(Long itemId, Integer amount) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with id: " + itemId));

        item.decreaseQuantity(amount);
        itemRepository.save(item);

        sendKafkaMessage(convertToSearchEntity(item));
    }

    private void sendKafkaMessage(ItemSearchEntity itemSearchDTO) {
        try {
            String itemJson = objectMapper.writeValueAsString(itemSearchDTO);
            kafkaTemplate.send("item-updates", itemSearchDTO.getId().toString(), itemJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize ItemSearchDTO", e);
        }
    }

    private ItemSearchEntity convertToSearchEntity(Item item) {
        return ItemSearchEntity.builder()
                .id(item.getId())
                .itemName(item.getItemName())
                .categoryName(item.getCategory().getCategoryName())
                .brand(item.getBrand())
                .price(item.getItemPrice().doubleValue())
                .stockQuantity(item.getStockQuantity())
                .sales(item.getSales())
                .isOnPromotion(item.getPromotionType() != PromotionType.NONE)
                .searchKeywords(item.getItemName() + " " + item.getBrand())  // 간단한 키워드 예시
                .status(item.getStatus().name())
                .detailImages(item.getItemDetail().getDetailImages())
                .build();
    }
}
