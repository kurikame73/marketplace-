package com.example.marketplace.domain.Item.repository;

import com.example.marketplace.domain.Item.dto.response.ItemDto;
import com.example.marketplace.domain.Item.entity.ItemStatus;
import com.example.marketplace.domain.Item.entity.PromotionType;
import com.example.marketplace.domain.Item.entity.QItem;
import com.example.marketplace.domain.Item.service.ItemFilter;
import com.example.marketplace.domain.category.entity.QCategory;
import com.example.marketplace.domain.category.repository.CategoryRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final CategoryRepository categoryRepository;

    @Override
    public Page<ItemDto> filteringItem(ItemFilter filter, Pageable pageable) {
        QItem item = QItem.item;
        QCategory category = QCategory.category;

        BooleanBuilder builder = new BooleanBuilder();
        addCategoryFilter(builder, item, filter.getCategoryId());
        addCategoryNameFilter(builder, category, filter.getCategoryName());
        addNameFilter(builder, item, filter.getItemName());
        addPriceRangeFilter(builder, item, filter.getMinPrice(), filter.getMaxPrice());
        addBrandFilter(builder, item, filter.getBrand());
        addPromotionTypeFilter(builder, item, filter.getPromotionType());
        addStatusFilter(builder, item, filter.getStatus());

        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        if (filter.getSortByList() != null && !filter.getSortByList().isEmpty()) {
            for (Sort.Order order : filter.getSortByList()) {
                OrderSpecifier<?> orderSpecifier;
                switch (order.getProperty()) {
                    case "discountRate":
                        orderSpecifier = order.isAscending() ? item.discountRate.asc() : item.discountRate.desc();
                        break;
                    case "itemPrice":
                        orderSpecifier = order.isAscending() ? item.itemPrice.asc() : item.itemPrice.desc();
                        break;
                    case "registeredDate":
                        orderSpecifier = order.isAscending() ? item.registeredDate.asc() : item.registeredDate.desc();
                        break;
                    case "recommendation":
                        orderSpecifier = order.isAscending() ? item.recommendations.size().asc() : item.recommendations.size().desc();
                        break;
                    case "sales":
                        orderSpecifier = order.isAscending() ? item.sales.asc() : item.sales.desc();
                        break;
                    default:
                        continue; // 또는 예외 처리
                }
                orderSpecifiers.add(orderSpecifier);
            }

        }

        List<ItemDto> items = queryFactory
                .select(Projections.constructor(ItemDto.class,
                        item.id,
                        item.itemName,
                        item.recommendations.size(),
                        item.sales,
                        item.promotionStart,
                        item.promotionEnd,
                        item.registeredDate,
                        item.stockQuantity,
                        item.itemPrice,
                        item.discountRate,
                        item.brand,
                        item.promotionType,
                        item.category.id,
                        item.reviews.size(),
                        item.category.categoryName))
                .from(item)
                .join(item.category)
                .where(builder)
                .orderBy(orderSpecifiers.isEmpty() ? new OrderSpecifier[]{item.id.desc()} : orderSpecifiers.toArray(new OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(item.count())
                .from(item)
                .where(builder);

        log.info("ItemRepositoryCustom result = {}", items);

        return PageableExecutionUtils.getPage(items, pageable, countQuery::fetchCount);
    }

    private void addCategoryFilter(BooleanBuilder builder, QItem item, Long categoryId) {
        if (categoryId != null) {
            List<Long> categoryIds = categoryRepository.findAllChildCategoryIds(categoryId);
            builder.and(item.category.id.in(categoryIds));
        }
    }

    private void addCategoryNameFilter(BooleanBuilder builder, QCategory category, String categoryName) {
        if (categoryName != null) {
            builder.and(category.categoryName.containsIgnoreCase(categoryName));
        }
    }

    private void addNameFilter(BooleanBuilder builder, QItem item, String itemName) {
        if (itemName != null && !itemName.isBlank()) {
            builder.and(item.itemName.containsIgnoreCase(itemName));
        }
    }

    private void addPriceRangeFilter(BooleanBuilder builder,
                                     QItem item, Integer minPrice,
                                     Integer maxPrice) {
        if (minPrice != null && maxPrice != null) {
            builder.and(item.itemPrice.between(minPrice, maxPrice));
        } else if (minPrice != null) {
            builder.and(item.itemPrice.goe(minPrice));
        } else if (maxPrice != null) {
            builder.and(item.itemPrice.loe(maxPrice));
        }
    }

    private void addBrandFilter(BooleanBuilder builder, QItem item, String brand) {
        if (brand != null && !brand.isBlank()) {
            builder.and(item.brand.equalsIgnoreCase(brand));
        }
    }

    private void addPromotionTypeFilter(BooleanBuilder builder, QItem item, PromotionType promotionType) {
        if (promotionType != null) {
            builder.and(item.promotionType.eq(promotionType));
        }
    }

    private void addStatusFilter(BooleanBuilder builder, QItem item, ItemStatus status) {
        if (status != null) {
            builder.and(item.status.eq(status));
        }
    }
}
