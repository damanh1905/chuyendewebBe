package com.example.chuyendeweb.model.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeToOrderByResponseAdmin {
	private Long id;
	private String userName;
	private Date dateCreated;
	private String address;
	private Date expectedDelivery;
	private double shipFee;
	private double totalPriceOrder;
	private String phoneNumber;
}
