//package com.example.marketplace.domain.ItemDetail.service;
//
//import com.example.marketplace.domain.Item.entity.Item;
//import com.example.marketplace.domain.Item.repository.ItemRepository;
//import com.example.marketplace.domain.ItemDetail.dto.response.GetItemDetailResponseDto;
//import com.example.marketplace.domain.ItemDetail.entity.ItemDetail;
//import com.example.marketplace.domain.ItemDetail.repository.ItemDetailRepository;
//import com.example.marketplace.domain.config.TestConfig;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@SpringBootTest
//@Import(TestConfig.class)
//@ActiveProfiles("test")
//@Slf4j
//class ItemDetailServiceTest {
//
//    @Autowired
//    private ItemDetailService itemDetailService;
//
//    @Autowired
//    private ItemRepository itemRepository;
//
//    @Autowired
//    private ItemDetailRepository itemDetailRepository;
//
//
//    @Test
//    void getItemDetailTest() {
//        // Given
//        // 테스트를 위한 ItemDetail 엔티티 생성 및 저장
//        Item item = Item.builder()
//                .itemName("seonlim")
//                .itemPrice(500).build();
//        itemRepository.save(item);
//        ItemDetail itemDetail = ItemDetail.builder()
//                .item(item)
//                .itemInfo("Test")
//                .sellerInfo("safasf")
//                .packagingType("Box")
//                .notes("Notes")
//                .detailImages(List.of("image1.jpg", "image2.jpg"))
//                .promotionImageUrl("promo.jpg")
//                .additionalDescription("add")
//        .build();
//        itemDetailRepository.save(itemDetail);
//        log.info("디테일1!!!!!!!! = {}" ,itemDetail.getItem().getId());
//        log.info("디테일!!!!!!!={}", itemDetail);
//
//        // When
//        // ItemDetail 조회
//        GetItemDetailResponseDto result = itemDetailService.getItemDetail(itemDetail.getItem().getId());
//        log.info("결과!!!!!!!!! = {}", result);
//
////        GetItemDetailResponseDto result = itemDetailService.getItemDetail(1L);
//
//        // Then
//        // 조회 결과 검증
//        assertThat(result.getItemId()).isEqualTo(itemDetail.getItem().getId());
//        assertThat(result.getItemInfo()).isEqualTo(itemDetail.getItemInfo());
//        assertThat(result.getSellerInfo()).isEqualTo(itemDetail.getSellerInfo());
//        assertThat(result.getPackagingType()).isEqualTo(itemDetail.getPackagingType());
//        assertThat(result.getNotes()).isEqualTo(itemDetail.getNotes());
//        assertThat(result.getDetailImages()).isEqualTo(itemDetail.getDetailImages());
//        assertThat(result.getPromotionImageUrl()).isEqualTo(itemDetail.getPromotionImageUrl());
//        assertThat(result.getAdditionalDescription()).isEqualTo(itemDetail.getAdditionalDescription());
//    }
//}