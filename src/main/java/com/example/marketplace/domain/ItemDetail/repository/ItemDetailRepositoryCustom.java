package com.example.marketplace.domain.ItemDetail.repository;

import com.example.marketplace.domain.ItemDetail.dto.response.GetItemDetailResponseDto;

public interface ItemDetailRepositoryCustom {
    GetItemDetailResponseDto findItemDetailByItemId(Long itemId);
}
