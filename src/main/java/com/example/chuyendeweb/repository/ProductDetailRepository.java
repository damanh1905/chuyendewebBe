package com.example.chuyendeweb.repository;

import com.example.chuyendeweb.entity.ProductDetailEntity;
import com.example.chuyendeweb.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductDetailRepository extends JpaRepository<ProductDetailEntity,Long> {
    @Query("select  p from  ProductDetailEntity p where  p.productEntity=?1")
    ProductDetailEntity findByProductEntity(ProductEntity productEntity);
}
