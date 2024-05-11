package com.example.marketplace.domain.Item.repository;

import com.example.marketplace.domain.Item.dto.response.ItemDto;
import com.example.marketplace.domain.Item.service.ItemFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

public interface ItemRepositoryCustom {
    Page<ItemDto> filteringItem(ItemFilter filter, Pageable pageable);
}
