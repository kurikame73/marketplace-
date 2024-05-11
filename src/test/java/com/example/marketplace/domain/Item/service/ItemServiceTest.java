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
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;

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

    @BeforeEach
    void setUp() {
        Category category1 = new Category(1L, "Electronics");
        Category category2 = new Category(2L, "Clothing");
        categoryRepository.saveAll(Arrays.asList(category1, category2));


        Item item1 = Item.builder()
                .itemName("Laptop")
                .itemPrice(100000)
                .brand("Brand X")
                .promotionType(PromotionType.WEEK_SPECIAL)
                .category(category1)
                .build();
        itemRepository.save(item1);

        Item item2 = Item.builder()
                .itemName("Smartphone")
                .itemPrice(50000)
                .brand("Brand Y")
                .promotionType(PromotionType.NONE)
                .category(category1)
                .build();
        itemRepository.save(item2);

        // 카테고리 2에 대한 아이템 생성
        Item item3 = Item.builder()
                .itemName("T-shirt")
                .itemPrice(2000)
                .brand("Brand A")
                .promotionType(PromotionType.NONE)
                .category(category2)
                .build();
        itemRepository.save(item3);

        Item item4 = Item.builder()
                .itemName("Jeans")
                .itemPrice(4000)
                .brand("Brand B")
                .promotionType(PromotionType.SEASONAL)
                .status(ItemStatus.DISCOUNT)
                .category(category2)
                .build();
        itemRepository.save(item4);

    }

    @Test
    void findItems_withoutFilters() {
        Pageable pageable = PageRequest.of(0, 10);
        ItemFilterDto itemFilterDto = new ItemFilterDto();
        Page<ItemDto> result = itemService.findItems(itemFilterDto, pageable);

        assertEquals(2, result.getTotalElements());
    }

    @Test
    void findItems_withCategoryFilter() throws JsonProcessingException {
        Pageable pageable = PageRequest.of(0, 10);
//        ItemFilterDto itemFilterDto = new ItemFilterDto();
//        itemFilterDto.setCategoryId(category.getId());
        ItemFilterDto itemFilterDto1 = new ItemFilterDto();
        itemFilterDto1.setCategoryId(1L);
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
        itemFilterDto.setCategoryId(2L);
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
        assertEquals(2L, itemDto.getCategoryId());
        assertEquals("Jeans", itemDto.getItemName());
        assertEquals(4000, itemDto.getItemPrice());
        assertEquals("Brand B", itemDto.getBrand());
        assertEquals(PromotionType.SEASONAL, itemDto.getPromotionType());
    }

}