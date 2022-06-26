package com.example.chuyendeweb.model.response;

import com.example.chuyendeweb.entity.ProductEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeToOrderRequest {
	 private int idProducts[];
	 private int feeTotal;
	 private int total;
	 //
	 private String address;
	 private String phoneNumber;
}
