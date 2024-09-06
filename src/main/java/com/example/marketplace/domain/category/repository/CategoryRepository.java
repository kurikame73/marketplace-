package com.example.marketplace.domain.category.repository;

import com.example.marketplace.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c.id FROM Category c WHERE c.id = :parentId OR c.parent.id = :parentId")
    List<Long> findAllChildCategoryIds(@Param("parentId") Long parentId);
}
