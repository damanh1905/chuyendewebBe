package com.example.chuyendeweb.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.chuyendeweb.exception.NotFoundException;
import com.example.chuyendeweb.model.response.ChangeToOrderRequest;
import com.example.chuyendeweb.model.response.ChangeToOrderResponseByUser;
import com.example.chuyendeweb.model.response.ResponseObject;
import com.example.chuyendeweb.security.CustomUserDetails;
import com.example.chuyendeweb.service.IOrderService;

@RestController
@RequestMapping("api/order")
public class OrderController {

	@Autowired
	IOrderService orderService;
	//checkout /xoa cart va/ luu cart vao order
	@PostMapping("checkoutOrder")
	public ResponseEntity<?> checkoutOrder(@RequestBody ChangeToOrderRequest changeToOrderRequest) {
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		orderService.saveToOrder(userDetails,changeToOrderRequest);
		// //System.out.println(changeToOrderRequest);
		// //System.out.println(userDetails);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "show CheckOut successful!", new Integer(3)));
	}
	//listOrderByIdUser
	@GetMapping("/listOrder")
	public ResponseEntity<?> getListOrderByUserId() {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		System.out.println(userDetails);
		List<ChangeToOrderResponseByUser> listNe=orderService.showListOrderByUserId(userDetails);
	
	return ResponseEntity.status(HttpStatus.OK)
			.body(new ResponseObject(HttpStatus.OK.value(), "show listOrderByUser successful!", listNe));
	}
//	deLeteOrderById
	@PostMapping("deleteOrder/{orderId}")
	public ResponseEntity<?> deleOrderDetail(@PathVariable(name = "orderId", required = true) Long orderId){
		orderService.deleteOrderByOrderId(orderId);
	      return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.value(), "product detail ", new Integer(5)));
	}
	

}
