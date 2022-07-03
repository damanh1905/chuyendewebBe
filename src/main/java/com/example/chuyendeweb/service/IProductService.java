package com.example.chuyendeweb.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.example.chuyendeweb.entity.ProductEntity;
import com.example.chuyendeweb.model.response.ProductResponse;

public interface IProductService {

    ProductResponse findById(Long productId);

    ProductEntity findByIdProduct(long id);

    Map<String, Object> showAndSearchProduct(String searchValue, Pageable pageable);

    Map<String, Object> showProductFilter(Map<String, Object> fiterParams, Long genderId, List<Long> category,
            List<Long> priceRanges, List<String> ordersProduct, int pageIndex, int pageSize);

    public List<ProductEntity> getRandomProduct(List<ProductEntity> list, int totalItems);
}
