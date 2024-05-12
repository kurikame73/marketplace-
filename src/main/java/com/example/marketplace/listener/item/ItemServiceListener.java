package com.example.marketplace.listener.item;

import com.example.marketplace.domain.Item.entity.Item;
import com.example.marketplace.domain.Item.entity.Recommendation;
import com.example.marketplace.domain.Item.repository.ItemRepository;
import com.example.marketplace.domain.Item.repository.RecommendationRepository;
import com.example.marketplace.domain.member.entity.Member;
import com.example.marketplace.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ItemServiceListener {
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final RecommendationRepository recommendationRepository;

    @EventListener
    public void handleItemRecommended(ItemRecommendedEvent event) {
        Member member = memberRepository.findById(event.memberId()).orElseThrow();
        Item item = itemRepository.findById(event.itemId()).orElseThrow();
        Recommendation recommendation = new Recommendation(member, item);
        log.info("리스너!!!!!!!!! = {}",recommendation);
        recommendationRepository.save(recommendation);
    }
}
