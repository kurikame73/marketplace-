package com.example.marketplace.domain.category.entity;

import com.example.marketplace.domain.Item.entity.Item;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> children = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    private List<Item> items = new ArrayList<>();
}
