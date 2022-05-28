package com.example.chuyendeweb.repository;

import com.example.chuyendeweb.entity.CartEntity;
import com.example.chuyendeweb.entity.CartItemEntity;
import com.example.chuyendeweb.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRespository extends JpaRepository<CartItemEntity, Long> {
    CartItemEntity findByCartEntityAndProductEntities(CartEntity cartEntity, ProductEntity productEntity);
    void delete(CartItemEntity cartItemEntity);

}
