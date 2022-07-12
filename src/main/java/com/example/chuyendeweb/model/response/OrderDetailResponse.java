package com.example.chuyendeweb.model.response;

import com.example.chuyendeweb.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {
    private int quantity;
    private double totalOrderDetailPrice;
    private ProductEntity productEntity;
}
