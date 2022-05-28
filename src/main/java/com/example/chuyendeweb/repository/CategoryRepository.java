package com.example.chuyendeweb.repository;

import com.example.chuyendeweb.entity.CategoryEntity;
import com.example.chuyendeweb.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository  extends JpaRepository<CategoryEntity,Long> {
    Optional<CategoryEntity> findById(Long id);
    CategoryEntity findByProductEntitys(ProductEntity productEntity);
}
