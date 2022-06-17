package com.example.chuyendeweb.service;

import com.example.chuyendeweb.entity.ProductEntity;
import com.example.chuyendeweb.model.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface IProductService {

    ProductResponse findById(Long productId);
    ProductEntity findByIdProduct(long id);
    Map<String,Object> showAndSearchProduct(String searchValue, Pageable pageable);
}
