package com.example.chuyendeweb.model.request;

import com.example.chuyendeweb.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChangeToCartReq {
    private Long productId;
    private int quantity;
}
