package com.example.chuyendeweb.service;

import java.util.List;
import java.util.Map;

import com.example.chuyendeweb.model.response.OrderDetailAdminResponse;
import com.example.chuyendeweb.security.CustomUserDetails;

public interface IOrderDetailService {

//	List<OrderDetailAdminResponse> showListOrderDetailByOrderId(Long id);

	Map<String, Object> showListOrderDetailByOrderId(Long id, CustomUserDetails userDetails);

}
