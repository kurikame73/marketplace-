package com.example.marketplace.domain.Item.repository;

import com.example.marketplace.domain.Item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

}
