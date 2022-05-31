package com.example.chuyendeweb.service;

import com.example.chuyendeweb.model.response.ProductResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {

    ProductResponse findById(Long productId);

    List<ProductResponse> searchProduct(String searchValue,Pageable pageable);

    Long getLength(String searchValue);
}
