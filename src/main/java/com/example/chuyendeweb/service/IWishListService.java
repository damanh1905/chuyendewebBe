package com.example.chuyendeweb.service;

import com.example.chuyendeweb.entity.ProductEntity;
import com.example.chuyendeweb.entity.UserEntity;
import com.example.chuyendeweb.entity.WishListEntity;
import com.example.chuyendeweb.entity.WishListItemEntity;
import com.example.chuyendeweb.model.response.ChangeToCartResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


import java.util.List;

public interface IWishListService {
    ProductEntity addProductWishList(Long id, Long id1);

    List<WishListItemEntity> findAll(Specification<WishListItemEntity> spec, Pageable pageable);
    WishListEntity findByUserEntity(UserEntity userEntity);

    void delete(ProductEntity productEntity);
}
