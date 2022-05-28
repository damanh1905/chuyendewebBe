package com.example.chuyendeweb.model.response;


import com.example.chuyendeweb.entity.CategoryEntity;
import com.example.chuyendeweb.entity.ProductDetailEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private int Price;
    private int Price_Sale;
    private int amount;
    private boolean isNew;
    private ProductDetailResponse productDetailResponse;
    private CategoryResponse categoryResponse;

}
