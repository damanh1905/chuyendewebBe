package com.example.chuyendeweb.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.example.chuyendeweb.entity.OrderDetailEntity;
import com.example.chuyendeweb.model.response.AdminChartCiResponse;
import com.example.chuyendeweb.model.response.AdminChartResponse;
import com.example.chuyendeweb.model.response.ChangeToOrderRequest;
import com.example.chuyendeweb.model.response.ChangeToOrderResponseByUser;
import com.example.chuyendeweb.security.CustomUserDetails;

public interface IOrderService {
	AdminChartCiResponse getChartDayMonth( int month,int year) throws ParseException;

	void saveToOrder(CustomUserDetails userDetails, ChangeToOrderRequest changeToOrderRequest);

	List<ChangeToOrderResponseByUser> showListOrderByUserId(CustomUserDetails userDetails);

	void deleteOrderByOrderId(Long orderId);

	Map<String, Object> showListOdersAdmin(int pageIndex, int pageSize);



	List<AdminChartResponse> getChartDay(int day,int month) throws ParseException;





	List<ChangeToOrderResponseByUser> showListOrderByUserIdAdmin(Long id);


	Map<String, Double> getChartDayMonthCi(int month, int year);
}
