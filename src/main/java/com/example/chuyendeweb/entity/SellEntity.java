package com.example.chuyendeweb.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sellEntity")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data

public class SellEntity extends BaseEntity {
	@Column
	private String email;
	@Column
	private String nameProduct;
	@Column
	private int price;
	@Column
	private String size;
	@Column
	private String brand;
	@Column
	private String description;
	@Column
	private String note;
//	listImage Sell
	@OneToMany(mappedBy = "sellEntity",cascade = CascadeType.ALL)
	List<ImageSell> imageSell;

	@ManyToOne
	@JoinColumn(name = "userId", referencedColumnName = "id")
	private UserEntity userEntity;
}
