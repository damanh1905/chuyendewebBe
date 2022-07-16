package com.example.chuyendeweb.controller.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.chuyendeweb.model.response.OrderDetailAdminResponse;
import com.example.chuyendeweb.model.response.ResponseObject;
import com.example.chuyendeweb.security.CustomUserDetails;
import com.example.chuyendeweb.service.IOrderDetailService;
import com.example.chuyendeweb.service.IOrderService;

@RestController
@RequestMapping("api/order/admin")
public class ManageOrderAdmin {
	@Autowired
	IOrderService orderService;
	@Autowired
	IOrderDetailService orderDetailService;
	
	@GetMapping("listOrders")
	public ResponseEntity<?> listOrdersAdmin(@RequestParam(defaultValue = "0") int pageIndex,@RequestParam(defaultValue = "12") int pageSize){
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("bạn chưa đăng nhập");
		}
		Map<String, Object> listOrders=this.orderService.showListOdersAdmin(pageIndex,pageSize);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "successfull", listOrders));
		
	}
	@PostMapping("deleteOrder/{id}")
	public ResponseEntity<?> deleteOrderAdmin(@PathVariable("id") Long id){
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("bạn chưa đăng nhập");
		}
		orderService.deleteOrderByOrderId(id);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "successfull", ""));
	}
	@PostMapping("orderDetailByIdOrder/{id}")
	public ResponseEntity<?> orderDetailByIdOrder(@PathVariable("id") Long id){
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("bạn chưa đăng nhập");
		}
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		System.out.println(userDetails);
//		List<OrderDetailAdminResponse> listOrderDetailResponse=orderDetailService.showListOrderDetailByOrderId(id,userDetails,pageIndex,pageSize);
		Map<String,Object> listOrderDetailResponse=orderDetailService.showListOrderDetailByOrderId(id,userDetails);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "successfull", listOrderDetailResponse));
	}

}
