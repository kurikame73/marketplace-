package com.example.marketplace.domain.Item.controller;

import com.example.marketplace.domain.Item.dto.request.ItemFilterDto;
import com.example.marketplace.domain.Item.dto.request.RecommendationRequestDto;
import com.example.marketplace.domain.Item.dto.response.ItemDto;
import com.example.marketplace.domain.Item.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/item-filter")
    public ResponseEntity<Page<ItemDto>> getItems(
            @RequestBody ItemFilterDto itemFilterDto,
            Pageable pageable) {
        log.info("ItemController findItem = {}", itemFilterDto);
        Page<ItemDto> items = itemService.findItems(itemFilterDto, pageable);
        return ResponseEntity.ok(items);
    }

    @PostMapping("/item-recommend")
    public ResponseEntity<String> recommendItem(@RequestBody RecommendationRequestDto dto) {
        itemService.itemRecommendation(dto.getItemId(), dto.getMemberId());
        return ResponseEntity.ok("추천 성공하였음.");
    }
}
