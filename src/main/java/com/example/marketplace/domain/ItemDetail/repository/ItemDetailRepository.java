package com.example.marketplace.domain.ItemDetail.repository;

import com.example.marketplace.domain.ItemDetail.entity.ItemDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDetailRepository extends JpaRepository<ItemDetail, Long>, ItemDetailRepositoryCustom {
}
