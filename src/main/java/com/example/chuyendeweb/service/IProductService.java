package com.example.chuyendeweb.service;

import com.example.chuyendeweb.model.response.ProductResponse;

public interface IProductService {

    ProductResponse findById(Long productId);
}
