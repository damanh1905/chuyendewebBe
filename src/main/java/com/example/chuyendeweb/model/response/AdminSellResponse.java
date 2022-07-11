package com.example.chuyendeweb.model.response;

import java.util.List;

import com.example.chuyendeweb.entity.ImageSell;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class AdminSellResponse {
	private Long id;
	private String brand;
	private String description;
	private String email;
	private String name;
	private String note;
	private int price;
	private String size;
	List<ImageAdminResponse> imageSell;

}
