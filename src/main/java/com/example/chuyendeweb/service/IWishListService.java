package com.example.chuyendeweb.service;

import com.example.chuyendeweb.entity.ProductEntity;
import com.example.chuyendeweb.model.response.ChangeToCartResponse;

public interface IWishListService {
    ProductEntity addProductWishList(Long id, Long id1);
}
