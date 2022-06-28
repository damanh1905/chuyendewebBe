package com.example.chuyendeweb.model.response;

import java.util.Date;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeToListSellResponse {
	private Long id;
	  private Date dateCreated;
	private String email;

	private String nameProduct;

	private int price;

	private String size;

	private String brand;

	private String description;

	private String note;

	@Override
	public String toString() {
		return "ChangeToListSellResponse [id=" + id + ", email=" + email + ", nameProduct=" + nameProduct + ", price="
				+ price + ", size=" + size + ", brand=" + brand + ", description=" + description + ", note=" + note
				+ "]";
	}

	

	

}
