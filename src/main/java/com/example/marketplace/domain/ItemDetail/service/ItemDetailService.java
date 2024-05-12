package com.example.marketplace.domain.ItemDetail.service;

import com.example.marketplace.domain.ItemDetail.dto.response.GetItemDetailResponseDto;
import com.example.marketplace.domain.ItemDetail.repository.ItemDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemDetailService {
    private final ItemDetailRepository itemDetailRepository;

    // 아이템 디테일 조회
    public GetItemDetailResponseDto getItemDetail(Long itemId) {
        return itemDetailRepository.findItemDetailByItemId(itemId);
    }
}
