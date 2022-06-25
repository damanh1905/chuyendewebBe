package com.example.chuyendeweb.service;

import java.util.List;

import com.example.chuyendeweb.model.response.ChangeToOrderRequest;
import com.example.chuyendeweb.model.response.ChangeToOrderResponseByUser;
import com.example.chuyendeweb.security.CustomUserDetails;

public interface IOrderService {
	void saveToOrder(CustomUserDetails userDetails, ChangeToOrderRequest changeToOrderRequest);

	List<ChangeToOrderResponseByUser> showListOrderByUserId(CustomUserDetails userDetails);
}
