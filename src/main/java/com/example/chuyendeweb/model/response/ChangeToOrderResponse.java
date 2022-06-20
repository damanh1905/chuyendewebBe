package com.example.chuyendeweb.model.response;

import com.example.chuyendeweb.entity.ProductEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeToOrderResponse {
	   private ProductEntity productEntities;
	    private int totalPrice;

}
