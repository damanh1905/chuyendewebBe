package com.example.chuyendeweb.repository;

import com.example.chuyendeweb.entity.ProductEntity;
import com.example.chuyendeweb.entity.WishListEntity;
import com.example.chuyendeweb.entity.WishListItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListItemRepository extends JpaRepository<WishListItemEntity, Long> {
    WishListItemEntity findByWishListAndProductEntities(WishListEntity wishList, ProductEntity productEntity);
}
