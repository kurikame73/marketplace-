//package com.example.marketplace.domain.Item.service;
//
//import com.example.marketplace.domain.Item.dto.request.ItemFilterDto;
//import com.example.marketplace.domain.Item.dto.response.ItemDto;
//import com.example.marketplace.domain.Item.entity.Item;
//import com.example.marketplace.domain.Item.entity.ItemStatus;
//import com.example.marketplace.domain.Item.entity.PromotionType;
//import com.example.marketplace.domain.Item.entity.Recommendation;
//import com.example.marketplace.domain.Item.repository.ItemRepository;
//import com.example.marketplace.domain.Item.repository.RecommendationRepository;
//import com.example.marketplace.domain.category.entity.Category;
//import com.example.marketplace.domain.category.repository.CategoryRepository;
//import com.example.marketplace.domain.config.TestConfig;
//import com.example.marketplace.domain.member.entity.Member;
//import com.example.marketplace.domain.member.repository.MemberRepository;
//import com.example.marketplace.listener.item.ItemRecommendedEvent;
//import com.example.marketplace.listener.item.ItemServiceListener;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectWriter;
//import jakarta.persistence.EntityManager;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.context.annotation.Import;
//import org.springframework.context.event.EventListener;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.time.LocalDateTime;
//import java.util.Comparator;
//import java.util.HashSet;
//import java.util.List;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//
//import static com.example.marketplace.domain.member.entity.QMember.member;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Import(TestConfig.class)
//@Slf4j
//@ActiveProfiles("test")
//class ItemServiceTest {
//
//    @Autowired
//    private ItemService itemService;
//
//    @Autowired
//    private ItemRepository itemRepository;
//
//    @Autowired
//    private ObjectMapper jacksonObjectMapper;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    private Category category;
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Autowired
//    private ApplicationEventPublisher eventPublisher;
//
//    @Autowired
//    private RecommendationRepository recommendationRepository;
//
//
//    @Autowired
//    private EntityManager em;
//
//    private Long itemId1, itemId2, itemId3, itemId4;
//
//    private Long memberId1, memberId2, memberId3, memberId4;
//
//    @BeforeEach
//    void setUp() {
//        Category parentCategory = categoryRepository.save(new Category("Parent Category"));
//
//        Category category1 = new Category("Electronics");
//        category1.setParent(parentCategory);
//        categoryRepository.save(category1);
//
//        Category category2 = new Category("Clothing");
//        category2.setParent(parentCategory);
//        categoryRepository.save(category2);
//
//        Member member1 = new Member("Member1", "member1@example.com");
//        Member member2 = new Member("Member2", "member2@example.com");
//        Member member3 = new Member("Member3", "member3@example.com");
//        Member member4 = new Member("Member4", "member4@example.com");
//
//
//        memberRepository.save(member1);
//        memberId1 = member1.getId();
//        memberRepository.save(member2);
//        memberId2 = member2.getId();
//        memberRepository.save(member3);
//        memberId3 = member3.getId();
//        memberRepository.save(member4);
//        memberId4 = member4.getId();
//
//
//
//        Item item1 = Item.builder()
//                .itemName("Laptop")
//                .itemPrice(100000)
//                .brand("Brand X")
//                .promotionType(PromotionType.WEEK_SPECIAL)
//                .category(category1)
//                .discountRate(10) // 추가
//                .registeredDate(LocalDateTime.now().minusDays(7)) // 추가
//                .sales(1000) // 추가
//                .recommendations(new HashSet<>())
//                .build();
//        itemRepository.save(item1);
//        itemId1 = item1.getId();
//        Recommendation recommendation11 = new Recommendation(member1, item1); // member는 추천한 회원 객체
//        item1.getRecommendations().add(recommendation11);
//        itemRepository.save(item1);
//
//        Item item2 = Item.builder()
//                .itemName("Smartphone")
//                .itemPrice(50000)
//                .brand("Brand Y")
//                .promotionType(PromotionType.NONE)
//                .category(category1)
//                .discountRate(0) // 추가
//                .registeredDate(LocalDateTime.now().minusDays(3)) // 추가
//                .sales(2000) // 추가
//                .recommendations(new HashSet<>())
//                .build();
//        itemRepository.save(item2);
//        itemId2 = item2.getId();
//        Recommendation recommendation21 = new Recommendation(member1, item2); // member는 추천한 회원 객체
//        Recommendation recommendation22 = new Recommendation(member2, item2); // member는 추천한 회원 객체
//        item2.getRecommendations().add(recommendation21);
//        item2.getRecommendations().add(recommendation22);
//        itemRepository.save(item2);
//
//
//        // 카테고리 2에 대한 아이템 생성
//        Item item3 = Item.builder()
//                .itemName("T-shirt")
//                .itemPrice(2000)
//                .brand("Brand A")
//                .promotionType(PromotionType.NONE)
//                .category(category2)
//                .discountRate(20) // 추가
//                .registeredDate(LocalDateTime.now().minusDays(10)) // 추가
//                .sales(500) // 추가
//                .recommendations(new HashSet<>())
//                .build();
//        itemRepository.save(item3);
//        itemId3 = item3.getId();
//        Recommendation recommendation31 = new Recommendation(member1, item3); // member는 추천한 회원 객체
//        Recommendation recommendation32 = new Recommendation(member2, item3); // member는 추천한 회원 객체
//        Recommendation recommendation33 = new Recommendation(member3, item3); // member는 추천한 회원 객체
//        item3.getRecommendations().add(recommendation31);
//        item3.getRecommendations().add(recommendation32);
//        item3.getRecommendations().add(recommendation33);
//        itemRepository.save(item3);
//
//
//        Item item4 = Item.builder()
//                .itemName("Jeans")
//                .itemPrice(4000)
//                .brand("Brand B")
//                .promotionType(PromotionType.SEASONAL)
//                .status(ItemStatus.DISCOUNT)
//                .category(category2)
//                .discountRate(30) // 추가
//                .registeredDate(LocalDateTime.now().minusDays(5)) // 추가
//                .sales(800) // 추가
//                .recommendations(new HashSet<>())
//                .build();
//        itemRepository.save(item4);
//        itemId4 = item4.getId();
//
//    }
//
//    @AfterEach
//    void tearDown() {
//        recommendationRepository.deleteAll();
//        itemRepository.deleteAll();
//        categoryRepository.deleteAll();
//        em.clear();
//    }
//
//    @Test
//    void findItems_withoutFilters() {
//        Pageable pageable = PageRequest.of(0, 10);
//        ItemFilterDto itemFilterDto = new ItemFilterDto();
//        Page<ItemDto> result = itemService.findItems(itemFilterDto, pageable);
//
//        assertEquals(4, result.getTotalElements());
//    }
//
//    @Test
//    void findItems_withCategoryFilter() throws JsonProcessingException {
//        Pageable pageable = PageRequest.of(0, 10);
//        ItemFilterDto itemFilterDto1 = new ItemFilterDto();
//        itemFilterDto1.setCategoryName("Electronics");
//        Page<ItemDto> result = itemService.findItems(itemFilterDto1, pageable);
//        ObjectWriter writer = jacksonObjectMapper.writerWithDefaultPrettyPrinter();
//        String jsonResult = writer.writeValueAsString(result);
//        log.info(jsonResult);
//        assertEquals(2, result.getTotalElements());
//    }
//
//    @Test
//    void findItems_withNameFilter() throws JsonProcessingException {
//        Pageable pageable = PageRequest.of(0, 10);
//        ItemFilterDto itemFilterDto = new ItemFilterDto();
//        itemFilterDto.setItemName("Laptop");
//        Page<ItemDto> result = itemService.findItems(itemFilterDto, pageable);
//        ObjectWriter writer = jacksonObjectMapper.writerWithDefaultPrettyPrinter();
//        log.info(writer.writeValueAsString(result));
//        assertEquals(1, result.getTotalElements());
//        assertEquals("Laptop", result.getContent().get(0).getItemName());
//    }
//
//    @Test
//    void findItems_withPriceRangeFilter() throws JsonProcessingException {
//        Pageable pageable = PageRequest.of(0, 10);
//        ItemFilterDto itemFilterDto = new ItemFilterDto();
//        itemFilterDto.setMinPrice(0);
//        itemFilterDto.setMaxPrice(999999);
//        Page<ItemDto> result = itemService.findItems(itemFilterDto, pageable);
//        ObjectWriter writer = jacksonObjectMapper.writerWithDefaultPrettyPrinter();
//        log.info(writer.writeValueAsString(result));
//        assertEquals(4, result.getTotalElements());
//        assertEquals(50000, result.getContent().get(2).getItemPrice());
//    }
//
//    @Test
//    void findItems_withBrandFilter() throws JsonProcessingException {
//        Pageable pageable = PageRequest.of(0, 10);
//        ItemFilterDto itemFilterDto = new ItemFilterDto();
//        itemFilterDto.setBrand("Brand X");
//        Page<ItemDto> result = itemService.findItems(itemFilterDto, pageable);
//        ObjectWriter writer = jacksonObjectMapper.writerWithDefaultPrettyPrinter();
//        log.info(writer.writeValueAsString(result));
//        assertEquals(1, result.getTotalElements());
//        assertEquals("Brand X", result.getContent().get(0).getBrand());
//    }
//
//    @Test
//    void findItems_withPromotionTypeFilter() throws JsonProcessingException {
//        Pageable pageable = PageRequest.of(0, 10);
//        ItemFilterDto itemFilterDto = new ItemFilterDto();
//        itemFilterDto.setPromotionType(PromotionType.WEEK_SPECIAL);
//        Page<ItemDto> result = itemService.findItems(itemFilterDto, pageable);
//        ObjectWriter writer = jacksonObjectMapper.writerWithDefaultPrettyPrinter();
//        log.info(writer.writeValueAsString(result));
//        assertEquals(1, result.getTotalElements());
//        assertEquals(PromotionType.WEEK_SPECIAL, result.getContent().get(0).getPromotionType());
//    }
//
//    @Test
//    void findItems_withStatusFilter() throws JsonProcessingException {
//        Pageable pageable = PageRequest.of(0, 10);
//        ItemFilterDto itemFilterDto = new ItemFilterDto();
//        itemFilterDto.setStatus(ItemStatus.DISCOUNT);
//        Page<ItemDto> result = itemService.findItems(itemFilterDto, pageable);
//        ObjectWriter writer = jacksonObjectMapper.writerWithDefaultPrettyPrinter();
//        log.info(writer.writeValueAsString(result));
//        assertEquals(1, result.getTotalElements());
//        assertSame(result.getContent().get(0).getPromotionType(), PromotionType.SEASONAL);
//    }
//
//    @Test
//    void findItems_withMultipleFilters() throws JsonProcessingException {
//        Pageable pageable = PageRequest.of(0, 10);
//        ItemFilterDto itemFilterDto = new ItemFilterDto();
//        itemFilterDto.setCategoryName("Clothing");
//        itemFilterDto.setItemName("Jeans");
//        itemFilterDto.setMinPrice(3000);
//        itemFilterDto.setMaxPrice(5000);
//        itemFilterDto.setBrand("Brand B");
//        itemFilterDto.setPromotionType(PromotionType.SEASONAL);
//        itemFilterDto.setStatus(ItemStatus.DISCOUNT);
//        Page<ItemDto> result = itemService.findItems(itemFilterDto, pageable);
//
//        ObjectWriter writer = jacksonObjectMapper.writerWithDefaultPrettyPrinter();
//        String jsonResult = writer.writeValueAsString(result);
//        log.info(jsonResult);
//
//        assertEquals(1, result.getTotalElements());
//        ItemDto itemDto = result.getContent().get(0);
//        assertEquals("Clothing", itemDto.getCategoryName());
//        assertEquals("Jeans", itemDto.getItemName());
//        assertEquals(4000, itemDto.getItemPrice());
//        assertEquals("Brand B", itemDto.getBrand());
//        assertEquals(PromotionType.SEASONAL, itemDto.getPromotionType());
//    }
//
//    @Test
//    void findItems_withSortByDiscountRateDesc() throws JsonProcessingException {
//        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "discountRate"));
//        ItemFilterDto itemFilterDto = new ItemFilterDto();
//        itemFilterDto.setSortByList(List.of(Sort.Order.desc("discountRate")));
//        Page<ItemDto> result = itemService.findItems(itemFilterDto, pageable);
//
//        ObjectWriter writer = jacksonObjectMapper.writerWithDefaultPrettyPrinter();
//        log.info(writer.writeValueAsString(result));
//
//        List<ItemDto> content = result.getContent();
//        assertThat(content).isSortedAccordingTo(Comparator.comparing(ItemDto::getDiscountRate).reversed());
//    }
//
//    @Test
//    void findItems_withSortByRegisteredDateDesc() throws JsonProcessingException {
//        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "registeredDate"));
//        ItemFilterDto itemFilterDto = new ItemFilterDto();
//        itemFilterDto.setSortByList(List.of(Sort.Order.desc("registeredDate")));
//        Page<ItemDto> result = itemService.findItems(itemFilterDto, pageable);
//
//        ObjectWriter writer = jacksonObjectMapper.writerWithDefaultPrettyPrinter();
//        log.info(writer.writeValueAsString(result));
//
//        List<ItemDto> content = result.getContent();
//        assertThat(content).isSortedAccordingTo(Comparator.comparing(ItemDto::getRegisteredDate).reversed());
//    }
//
//    @Test
//    void findItems_withSortByItemPriceAsc() throws JsonProcessingException {
//        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "itemPrice"));
//        ItemFilterDto itemFilterDto = new ItemFilterDto();
//        itemFilterDto.setSortByList(List.of(Sort.Order.asc("itemPrice")));
//        Page<ItemDto> result = itemService.findItems(itemFilterDto, pageable);
//
//        ObjectWriter writer = jacksonObjectMapper.writerWithDefaultPrettyPrinter();
//        log.info(writer.writeValueAsString(result));
//
//        List<ItemDto> content = result.getContent();
//        assertThat(content).isSortedAccordingTo(Comparator.comparing(ItemDto::getItemPrice));
//    }
//
//    @Test
//    void testItemRecommendation() {
//        // Given
//        Member member666 = new Member("Member666", "member666@example.com");
//        memberRepository.save(member666);
//        Item item666 = Item.builder()
//                .itemName("Laptoppp")
//                .itemPrice(10000000)
//                .brand("Brand XY")
//                .promotionType(PromotionType.WEEK_SPECIAL)
//                .recommendations(new HashSet<>())
//                .build();
//        itemRepository.save(item666);
//        Long itemId666 = item666.getId();
//
//        // When
//        eventPublisher.publishEvent(new ItemRecommendedEvent(itemId666, member666.getId()));
//
//        // Then
//        Recommendation recommendation = recommendationRepository.findByMemberId(member666.getId()).orElseThrow();
//        assertEquals(recommendation.getMember().getId(), member666.getId());
//        assertEquals(recommendation.getItem().getId(), itemId666);
//    }
//}