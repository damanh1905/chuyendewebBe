package com.example.chuyendeweb.model.response;

import java.util.List;

import com.example.chuyendeweb.entity.ImageEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderAdminResponse {
	 private Long id;
	 private int price;
	 private String name;
	 List<ImageAdminProductResponse> ImageEntity;
}
