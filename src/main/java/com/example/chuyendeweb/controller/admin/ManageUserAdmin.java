package com.example.chuyendeweb.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.example.chuyendeweb.common.ERole;
import com.example.chuyendeweb.entity.OrderDetailEntity;
import com.example.chuyendeweb.entity.OrderEntity;
import com.example.chuyendeweb.entity.RoleEntity;
import com.example.chuyendeweb.entity.UserEntity;
import com.example.chuyendeweb.model.response.AdminUserResponse;
import com.example.chuyendeweb.model.response.ChangeToOrderResponseByUser;
import com.example.chuyendeweb.model.response.ResponseObject;
import com.example.chuyendeweb.model.response.UserReponse;
import com.example.chuyendeweb.repository.RoleRepository;
import com.example.chuyendeweb.repository.UserRepository;
import com.example.chuyendeweb.security.CustomUserDetails;
import com.example.chuyendeweb.service.IOrderService;
import com.example.chuyendeweb.service.IProductService;
import com.example.chuyendeweb.service.IUserService;

@RestController
@RequestMapping("api/manage/admin")
public class ManageUserAdmin {
	@Autowired 
	UserRepository userRepository;
	@Autowired
	IUserService iUserService;
	@Autowired
	ModelMapper mapper;
	@Autowired
	RoleRepository roleRepo;
	@Autowired 
	IOrderService iOrderService;
	@GetMapping("/findAllUsers")
	public ResponseEntity<?> findAllUsers(@RequestParam(defaultValue = "0") int pageIndex,	@RequestParam(defaultValue = "12") int pageSize,@RequestParam(required = false) List<String> sortBy){
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("bạn chưa đăng nhập");
		}
		Map<String, Object> listUser=iUserService.showListUser(pageIndex,pageSize);
		 if (listUser == null)
             return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(new ResponseObject(HttpStatus.NOT_FOUND.value(), "Not found list User",
                                             ""));
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "successfull", listUser));
		
	}
	@PostMapping("/deleteUsers")
	public ResponseEntity<?> deletelistUser(@RequestParam("listId") Long [] ids){
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("bạn chưa đăng nhập");
		}
			this.iUserService.deleteIds(ids);

	
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "delete Successfull", "delete Successfull"));
		
		
	}
	@PostMapping("userDetail/{id}")
	public ResponseEntity<?> userDetail(@PathVariable("id") Long id){
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("bạn chưa đăng nhập");
		}
		System.out.println(id);
		UserEntity user=this.iUserService.findById(id);
		AdminUserResponse userResponse=mapper.map(user, AdminUserResponse.class);
		if (userResponse==null) {
			  return ResponseEntity.status(HttpStatus.NOT_FOUND)
                      .body(new ResponseObject(HttpStatus.NOT_FOUND.value(), "Not found list User",
                                      ""));
		}
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
//		xu li toang tien va tong don hang
		List<ChangeToOrderResponseByUser> list=iOrderService.showListOrderByUserIdAdmin(id);
		int total=0;
		int totalOrder=0;
		for (ChangeToOrderResponseByUser changeToOrderResponseByUser : list) {
				total+=changeToOrderResponseByUser.getTotalPriceOrder();
				totalOrder+=1;
		}
		userResponse.setTotalPrice(total);
		userResponse.setTotalOrder(totalOrder);
//
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "show User Detail Successfull", userResponse));
	}
	@PostMapping("listOrderByUser/{id}")
	public ResponseEntity<?> listOrderDetailByUser(@PathVariable("id") Long id){
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("bạn chưa đăng nhập");
		}
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
	
		List<ChangeToOrderResponseByUser> listDetail=iOrderService.showListOrderByUserIdAdmin(id);
		Map<String, Object> result=new HashMap<String, Object>();
		result.put("listDetail", listDetail);
		UserEntity user=userRepository.findOnedById(id);
		result.put("userName",user.getUserName());
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "show User Detail Successfull", result));
	}
		
	}
	
	
	
	


