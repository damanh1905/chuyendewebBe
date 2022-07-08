package com.example.chuyendeweb.model.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ListOrderAdminResponse {
	private Long id;
	private Date dateCreated;
	private String address;
	private String userName;
	private double totalPriceOrder;
	private userOrderResponse userEntity;

}
