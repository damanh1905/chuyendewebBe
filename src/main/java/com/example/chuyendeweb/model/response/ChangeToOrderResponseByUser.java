package com.example.chuyendeweb.model.response;

import java.util.Date;

import javax.persistence.Column;

import com.example.chuyendeweb.entity.ProductEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeToOrderResponseByUser {
    private Long id;
	  private Date dateCreated;
	private String address;
	private Date expectedDelivery;
	private double shipFee;
	private double totalPriceOrder;
	private String phoneNumber;

}
