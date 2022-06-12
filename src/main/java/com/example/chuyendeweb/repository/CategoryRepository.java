package com.example.chuyendeweb.repository;

import com.example.chuyendeweb.entity.CategoryEntity;
import com.example.chuyendeweb.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CategoryRepository  extends JpaRepository<CategoryEntity,Long> {
    Optional<CategoryEntity> findById(Long id);
    CategoryEntity findByProductEntitys(ProductEntity productEntity);
  @Query(value = "select  * FROM category c WHERE c.NameCategory = ?1 ",nativeQuery = true)
    CategoryEntity findByNameCategory(String nameCategory);
}
