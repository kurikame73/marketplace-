package com.example.marketplace.domain.ItemDetail.controller;

import com.example.marketplace.domain.ItemDetail.dto.response.GetItemDetailResponseDto;
import com.example.marketplace.domain.ItemDetail.service.ItemDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item-detail")
@RequiredArgsConstructor
public class ItemDetailController {
    private final ItemDetailService itemDetailService;

    public ResponseEntity<GetItemDetailResponseDto> getItemDetail(@RequestParam Long itemId) {
        return ResponseEntity.ok(itemDetailService.getItemDetail(itemId));
    }
}
