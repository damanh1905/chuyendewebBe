package com.example.chuyendeweb.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ImageSell extends BaseEntity{
	@Lob
	private String base64;
	@ManyToOne
    @JoinColumn(name = "sellId",referencedColumnName = "id")
    private SellEntity sellEntity;

}
