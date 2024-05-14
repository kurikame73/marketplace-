package com.example.marketplace.domain.wishlist.entity;

import com.example.marketplace.domain.Item.entity.Item;
import com.example.marketplace.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Wishlist {

    @Id
    @GeneratedValue
    @Column(name = "wishlist_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToMany
    @JoinTable(
            name = "wishlist_items",
            joinColumns = @JoinColumn(name = "wishlist_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items = new ArrayList<>();

    public static Wishlist addWishlist(Member member, Item item) {
        Wishlist wishlist = Wishlist.builder()
                .member(member)
                .build();
        wishlist.items.add(item);
        return wishlist;
    }
}
