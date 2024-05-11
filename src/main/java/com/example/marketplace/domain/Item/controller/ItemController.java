package com.example.marketplace.domain.Item.controller;

import com.example.marketplace.domain.Item.dto.request.ItemFilterDto;
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

    @GetMapping
    public ResponseEntity<Page<ItemDto>> getItems(
            @RequestBody ItemFilterDto itemFilterDto,
            Pageable pageable) {
        Page<ItemDto> items = itemService.findItems(itemFilterDto, pageable);
        return ResponseEntity.ok(items);
    }
}
