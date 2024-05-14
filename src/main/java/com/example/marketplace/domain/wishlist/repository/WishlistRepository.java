package com.example.marketplace.domain.wishlist.repository;

import com.example.marketplace.domain.wishlist.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findAllByMemberId(Long itemId);
}
