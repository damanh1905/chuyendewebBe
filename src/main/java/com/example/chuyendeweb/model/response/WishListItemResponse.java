package com.example.chuyendeweb.model.response;

import com.example.chuyendeweb.entity.ProductEntity;
import com.example.chuyendeweb.entity.WishListEntity;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WishListItemResponse {
    private ProductEntity productEntities ;
    private WishListEntity wishList ;
}
