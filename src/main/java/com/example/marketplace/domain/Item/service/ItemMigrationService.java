package com.example.marketplace.domain.Item.service;

import com.example.marketplace.domain.Item.entity.Item;
import com.example.marketplace.domain.Item.entity.ItemSearchEntity;
import com.example.marketplace.domain.Item.entity.PromotionType;
import com.example.marketplace.domain.Item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemMigrationService {
    private static final Logger log = LoggerFactory.getLogger(ItemMigrationService.class);
    private final ItemRepository itemRepository;
    private final RestTemplate restTemplate;

    private final String searchServerUrl = "http://localhost:9000/migrate";
    private static final int BATCH_SIZE = 1000;  // 배치 크기 설정


    public void migrateDataToSearchServer() {
        List<Item> items = itemRepository.findAll();

        // 전체 데이터를 배치 크기 단위로 나누어 처리
        for (int i = 0; i < items.size(); i += BATCH_SIZE) {
            int end = Math.min(i + BATCH_SIZE, items.size());
            List<Item> batchItems = items.subList(i, end);

            List<ItemSearchEntity> searchEntities = batchItems.stream()
                    .map(this::convertToSearchEntity)
                    .collect(Collectors.toList());

            // HttpHeaders 및 HttpEntity를 사용하여 요청 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON); // Content-Type을 JSON으로 설정
            HttpEntity<List<ItemSearchEntity>> request = new HttpEntity<>(searchEntities, headers); // 데이터와 헤더를 HttpEntity로 묶음

            log.info("Request seonlim = {}", request.getBody());
            // 배치 데이터 전송

            try {
                ResponseEntity<String> response = restTemplate.postForEntity(searchServerUrl, request, String.class);
                log.info("Response: {}", response);
            } catch (HttpClientErrorException e) {
                log.error("Client error: {}", e.getResponseBodyAsString(), e);
            } catch (Exception e) {
                log.error("Error occurred while migrating data", e);
            }
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
