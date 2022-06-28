package com.example.chuyendeweb.model.request;

import java.io.File;
import java.util.ArrayList;

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
public class ChangeToSellReq {
	private String brand;
	private String description;
	private String email;
	private String name;
	private String note;
	private int price;
	private String size;
	private String[] upload;
	
	
	

}
