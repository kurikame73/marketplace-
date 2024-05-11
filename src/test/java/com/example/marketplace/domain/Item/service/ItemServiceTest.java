package com.example.marketplace.domain.Item.service;

import com.example.marketplace.domain.Item.dto.request.ItemFilterDto;
import com.example.marketplace.domain.Item.dto.response.ItemDto;
import com.example.marketplace.domain.Item.entity.Item;
import com.example.marketplace.domain.Item.entity.ItemStatus;
import com.example.marketplace.domain.Item.entity.PromotionType;
import com.example.marketplace.domain.Item.repository.ItemRepository;
import com.example.marketplace.domain.category.entity.Category;
import com.example.marketplace.domain.category.repository.CategoryRepository;
import com.example.marketplace.domain.config.TestConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TestConfig.class)
@Slf4j
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    private Category category;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    void setUp() {
        Category parentCategory = categoryRepository.save(new Category("Parent Category"));

        Category category1 = new Category("Electronics");
        category1.setParent(parentCategory);
        categoryRepository.save(category1);

        Category category2 = new Category("Clothing");
        category2.setParent(parentCategory);
        categoryRepository.save(category2);


        Item item1 = Item.builder()
                .itemName("Laptop")
                .itemPrice(100000)
                .brand("Brand X")
                .promotionType(PromotionType.WEEK_SPECIAL)
                .category(category1)
                .discountRate(10) // 추가
                .registeredDate(LocalDateTime.now().minusDays(7)) // 추가
                .sales(1000) // 추가
                .recommendation(45) // 추가
                .build();
        itemRepository.save(item1);

        Item item2 = Item.builder()
                .itemName("Smartphone")
                .itemPrice(50000)
                .brand("Brand Y")
                .promotionType(PromotionType.NONE)
                .category(category1)
                .discountRate(0) // 추가
                .registeredDate(LocalDateTime.now().minusDays(3)) // 추가
                .sales(2000) // 추가
                .recommendation(40) // 추가
                .build();
        itemRepository.save(item2);

        // 카테고리 2에 대한 아이템 생성
        Item item3 = Item.builder()
                .itemName("T-shirt")
                .itemPrice(2000)
                .brand("Brand A")
                .promotionType(PromotionType.NONE)
                .category(category2)
                .discountRate(20) // 추가
                .registeredDate(LocalDateTime.now().minusDays(10)) // 추가
                .sales(500) // 추가
                .recommendation(38) // 추가
                .build();
        itemRepository.save(item3);

        Item item4 = Item.builder()
                .itemName("Jeans")
                .itemPrice(4000)
                .brand("Brand B")
                .promotionType(PromotionType.SEASONAL)
                .status(ItemStatus.DISCOUNT)
                .category(category2)
                .discountRate(30) // 추가
                .registeredDate(LocalDateTime.now().minusDays(5)) // 추가
                .sales(800) // 추가
                .recommendation(22) // 추가
                .build();
        itemRepository.save(item4);
    }

    @AfterEach
    void tearDown() {
        itemRepository.deleteAll();
        categoryRepository.deleteAll();
        em.clear();
    }

    @Test
    void findItems_withoutFilters() {
        Pageable pageable = PageRequest.of(0, 10);
        ItemFilterDto itemFilterDto = new ItemFilterDto();
        Page<ItemDto> result = itemService.findItems(itemFilterDto, pageable);

        assertEquals(4, result.getTotalElements());
    }

    @Test
    void findItems_withCategoryFilter() throws JsonProcessingException {
        Pageable pageable = PageRequest.of(0, 10);
        ItemFilterDto itemFilterDto1 = new ItemFilterDto();
        itemFilterDto1.setCategoryName("Electronics");
        Page<ItemDto> result = itemService.findItems(itemFilterDto1, pageable);
        ObjectWriter writer = jacksonObjectMapper.writerWithDefaultPrettyPrinter();
        String jsonResult = writer.writeValueAsString(result);
        log.info(jsonResult);
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void findItems_withNameFilter() throws JsonProcessingException {
        Pageable pageable = PageRequest.of(0, 10);
        ItemFilterDto itemFilterDto = new ItemFilterDto();
        itemFilterDto.setItemName("Laptop");
        Page<ItemDto> result = itemService.findItems(itemFilterDto, pageable);
        ObjectWriter writer = jacksonObjectMapper.writerWithDefaultPrettyPrinter();
        log.info(writer.writeValueAsString(result));
        assertEquals(1, result.getTotalElements());
        assertEquals("Laptop", result.getContent().get(0).getItemName());
    }

    @Test
    void findItems_withPriceRangeFilter() throws JsonProcessingException {
        Pageable pageable = PageRequest.of(0, 10);
        ItemFilterDto itemFilterDto = new ItemFilterDto();
        itemFilterDto.setMinPrice(0);
        itemFilterDto.setMaxPrice(999999);
        Page<ItemDto> result = itemService.findItems(itemFilterDto, pageable);
        ObjectWriter writer = jacksonObjectMapper.writerWithDefaultPrettyPrinter();
        log.info(writer.writeValueAsString(result));
        assertEquals(4, result.getTotalElements());
        assertEquals(50000, result.getContent().get(2).getItemPrice());
    }

    @Test
    void findItems_withBrandFilter() throws JsonProcessingException {
        Pageable pageable = PageRequest.of(0, 10);
        ItemFilterDto itemFilterDto = new ItemFilterDto();
        itemFilterDto.setBrand("Brand X");
        Page<ItemDto> result = itemService.findItems(itemFilterDto, pageable);
        ObjectWriter writer = jacksonObjectMapper.writerWithDefaultPrettyPrinter();
        log.info(writer.writeValueAsString(result));
        assertEquals(1, result.getTotalElements());
        assertEquals("Brand X", result.getContent().get(0).getBrand());
    }

    @Test
    void findItems_withPromotionTypeFilter() throws JsonProcessingException {
        Pageable pageable = PageRequest.of(0, 10);
        ItemFilterDto itemFilterDto = new ItemFilterDto();
        itemFilterDto.setPromotionType(PromotionType.WEEK_SPECIAL);
        Page<ItemDto> result = itemService.findItems(itemFilterDto, pageable);
        ObjectWriter writer = jacksonObjectMapper.writerWithDefaultPrettyPrinter();
        log.info(writer.writeValueAsString(result));
        assertEquals(1, result.getTotalElements());
        assertEquals(PromotionType.WEEK_SPECIAL, result.getContent().get(0).getPromotionType());
    }

    @Test
    void findItems_withStatusFilter() throws JsonProcessingException {
        Pageable pageable = PageRequest.of(0, 10);
        ItemFilterDto itemFilterDto = new ItemFilterDto();
        itemFilterDto.setStatus(ItemStatus.DISCOUNT);
        Page<ItemDto> result = itemService.findItems(itemFilterDto, pageable);
        ObjectWriter writer = jacksonObjectMapper.writerWithDefaultPrettyPrinter();
        log.info(writer.writeValueAsString(result));
        assertEquals(1, result.getTotalElements());
        assertSame(result.getContent().get(0).getPromotionType(), PromotionType.SEASONAL);
    }

    @Test
    void findItems_withMultipleFilters() throws JsonProcessingException {
        Pageable pageable = PageRequest.of(0, 10);
        ItemFilterDto itemFilterDto = new ItemFilterDto();
        itemFilterDto.setCategoryName("Clothing");
        itemFilterDto.setItemName("Jeans");
        itemFilterDto.setMinPrice(3000);
        itemFilterDto.setMaxPrice(5000);
        itemFilterDto.setBrand("Brand B");
        itemFilterDto.setPromotionType(PromotionType.SEASONAL);
        itemFilterDto.setStatus(ItemStatus.DISCOUNT);
        Page<ItemDto> result = itemService.findItems(itemFilterDto, pageable);

        ObjectWriter writer = jacksonObjectMapper.writerWithDefaultPrettyPrinter();
        String jsonResult = writer.writeValueAsString(result);
        log.info(jsonResult);

        assertEquals(1, result.getTotalElements());
        ItemDto itemDto = result.getContent().get(0);
        assertEquals("Clothing", itemDto.getCategoryName());
        assertEquals("Jeans", itemDto.getItemName());
        assertEquals(4000, itemDto.getItemPrice());
        assertEquals("Brand B", itemDto.getBrand());
        assertEquals(PromotionType.SEASONAL, itemDto.getPromotionType());
    }

    @Test
    void findItems_withSortByDiscountRateDesc() throws JsonProcessingException {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "discountRate"));
        ItemFilterDto itemFilterDto = new ItemFilterDto();
        itemFilterDto.setSortByList(List.of(Sort.Order.desc("discountRate")));
        Page<ItemDto> result = itemService.findItems(itemFilterDto, pageable);

        ObjectWriter writer = jacksonObjectMapper.writerWithDefaultPrettyPrinter();
        log.info(writer.writeValueAsString(result));

        List<ItemDto> content = result.getContent();
        assertThat(content).isSortedAccordingTo(Comparator.comparing(ItemDto::getDiscountRate).reversed());
    }

    @Test
    void findItems_withSortByRegisteredDateDesc() throws JsonProcessingException {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "registeredDate"));
        ItemFilterDto itemFilterDto = new ItemFilterDto();
        itemFilterDto.setSortByList(List.of(Sort.Order.desc("registeredDate")));
        Page<ItemDto> result = itemService.findItems(itemFilterDto, pageable);

        ObjectWriter writer = jacksonObjectMapper.writerWithDefaultPrettyPrinter();
        log.info(writer.writeValueAsString(result));

        List<ItemDto> content = result.getContent();
        assertThat(content).isSortedAccordingTo(Comparator.comparing(ItemDto::getRegisteredDate).reversed());
    }

    @Test
    void findItems_withSortByItemPriceAsc() throws JsonProcessingException {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "itemPrice"));
        ItemFilterDto itemFilterDto = new ItemFilterDto();
        itemFilterDto.setSortByList(List.of(Sort.Order.asc("itemPrice")));
        Page<ItemDto> result = itemService.findItems(itemFilterDto, pageable);

        ObjectWriter writer = jacksonObjectMapper.writerWithDefaultPrettyPrinter();
        log.info(writer.writeValueAsString(result));

        List<ItemDto> content = result.getContent();
        assertThat(content).isSortedAccordingTo(Comparator.comparing(ItemDto::getItemPrice));
    }

    @Test
    void findItems_withSortByRecommendationAsc() throws JsonProcessingException {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "recommendation"));
        ItemFilterDto itemFilterDto = new ItemFilterDto();
        itemFilterDto.setSortByList(List.of(Sort.Order.asc("recommendation")));
        Page<ItemDto> result = itemService.findItems(itemFilterDto, pageable);

        ObjectWriter writer = jacksonObjectMapper.writerWithDefaultPrettyPrinter();
        log.info(writer.writeValueAsString(result));

        List<ItemDto> content = result.getContent();
        assertThat(content).isSortedAccordingTo(Comparator.comparing(ItemDto::getRecommendation));
    }
}