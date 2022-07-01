package com.example.chuyendeweb.model.response;

import com.example.chuyendeweb.entity.ProductEntity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ChangeToCartResponse {
    private ProductResponse productEntities;
    private int quantity;
    private int totalPrice;
}
