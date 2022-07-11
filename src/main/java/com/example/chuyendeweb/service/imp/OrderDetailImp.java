package com.example.chuyendeweb.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.chuyendeweb.entity.OrderDetailEntity;
import com.example.chuyendeweb.entity.OrderEntity;
import com.example.chuyendeweb.model.response.OrderDetailAdminResponse;
import com.example.chuyendeweb.repository.OrderDetailRepository;
import com.example.chuyendeweb.repository.OrderRepository;
import com.example.chuyendeweb.security.CustomUserDetails;
import com.example.chuyendeweb.service.IOrderDetailService;
import com.example.chuyendeweb.service.IOrderService;
@Service
public class OrderDetailImp implements IOrderDetailService {
	@Autowired
	OrderDetailRepository orderDetailRepo;
	@Autowired
	ModelMapper mapper;
	@Autowired
	OrderRepository orderRepo;
//	@Override
//	public List<OrderDetailAdminResponse> showListOrderDetailByOrderId(Long id) {
//		List<OrderDetailAdminResponse> result=new ArrayList<OrderDetailAdminResponse>();
//		List<OrderDetailEntity> listOrderDetail=orderDetailRepo.findByOrderEntityId(id);
//		for (OrderDetailEntity orderDetailEntity : listOrderDetail) {
//			result.add(mapper.map(orderDetailEntity, OrderDetailAdminResponse.class));
//			
//		}
//		return result;
//	}
	@Override
	public Map<String, Object> showListOrderDetailByOrderId(Long id, CustomUserDetails userDetails) {
			int total=0;
			Map<String, Object> result=new HashMap<String, Object>();
			List<OrderDetailAdminResponse> listOrderAdmin=new ArrayList<OrderDetailAdminResponse>();
			List<OrderDetailEntity> listOrderDetail=orderDetailRepo.findByOrderEntityId(id);
			for (OrderDetailEntity orderDetailEntity : listOrderDetail) {
				listOrderAdmin.add(mapper.map(orderDetailEntity, OrderDetailAdminResponse.class));
				total+=1;
				
			}
			OrderEntity order=orderRepo.findOneById(id);
			result.put("listOrderDetail", listOrderAdmin);
			result.put("nameUser", userDetails.getUsername());
			result.put("idUser", userDetails.getId());
			result.put("addressOrder", order.getAddress());
			result.put("phone", order.getPhoneNumber());
			result.put("totalOrderDetail", total);
			
		return result;
	}
	

}
