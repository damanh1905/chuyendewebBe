package com.example.chuyendeweb.repository;

import com.example.chuyendeweb.entity.CartEntity;
import com.example.chuyendeweb.entity.CartItemEntity;
import com.example.chuyendeweb.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    CartItemEntity findByCartEntityAndProductEntities(CartEntity cartEntity, ProductEntity productEntity);
    @Modifying
    void delete(CartItemEntity cartItemEntity);
    void deleteById(Long id);
    
    @Transactional
    void deleteAllByCartEntity(CartEntity cartEntity); 

}
