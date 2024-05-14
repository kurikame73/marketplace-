package com.example.marketplace.domain.wishlist.service;

import com.example.marketplace.domain.Item.entity.Item;
import com.example.marketplace.domain.Item.repository.ItemRepository;
import com.example.marketplace.domain.member.entity.Member;
import com.example.marketplace.domain.member.repository.MemberRepository;
import com.example.marketplace.domain.wishlist.entity.Wishlist;
import com.example.marketplace.domain.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService {
    private final MemberRepository memberRepository;
    private final WishlistRepository wishlistRepository;
    private final ItemRepository itemRepository;

    // 위시리스트 추가
    public void addWishlist(Long itemId, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Item item = itemRepository.findById(itemId).orElseThrow();
        wishlistRepository.save(Wishlist.addWishlist(member, item));
    }

    // 위시리스트 조회
    public List<Wishlist> getWishlist(Long memberId) {
        return wishlistRepository.findAllByMemberId(memberId);
    }
}
