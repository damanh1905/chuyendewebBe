package com.example.chuyendeweb.entity;

import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orderDetail")
public class OrderDetailEntity extends BaseEntity {
	private int quantity;
	private double totalOrderDetailPrice;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "orderId", referencedColumnName = "id")
	private OrderEntity orderEntity;
	
	   @JsonIgnore
	    @ManyToOne
	    @JoinColumn(name = "productId",referencedColumnName = "id")
	    private ProductEntity productEntity;
	


}
