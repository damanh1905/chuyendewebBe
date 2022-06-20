package com.example.chuyendeweb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "orderEntity")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class OrderEntity extends BaseEntity {
	private String address;
	@Column
	private Date expectedDelivery;
	@Column
	private double shipFee;
	@Column
	private double originalPrice;
	@Column
	private double totalPriceOrder;
	@Column
	private String paymenId;
	@Column
	private Date dateCreate;
	@Column
	private Date dateDelivery;
	// user voi
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "userID", referencedColumnName = "id")
	private UserEntity userEntity;

	//
	@JsonIgnore
	@OneToMany(mappedBy = "orderEntity")
	private List<OrderDetailEntity> orderDetail;
	

}
