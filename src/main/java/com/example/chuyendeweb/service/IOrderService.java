package com.example.chuyendeweb.service;

import com.example.chuyendeweb.model.response.ChangeToOrderRequest;
import com.example.chuyendeweb.security.CustomUserDetails;

public interface IOrderService {
	void saveToOrder(CustomUserDetails userDetails, ChangeToOrderRequest changeToOrderRequest);
}
