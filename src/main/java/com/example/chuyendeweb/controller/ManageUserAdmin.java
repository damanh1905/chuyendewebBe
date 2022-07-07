package com.example.chuyendeweb.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.chuyendeweb.common.ERole;
import com.example.chuyendeweb.entity.RoleEntity;
import com.example.chuyendeweb.entity.UserEntity;
import com.example.chuyendeweb.model.response.AdminUserResponse;
import com.example.chuyendeweb.model.response.ResponseObject;
import com.example.chuyendeweb.model.response.UserReponse;
import com.example.chuyendeweb.repository.RoleRepository;
import com.example.chuyendeweb.repository.UserRepository;
import com.example.chuyendeweb.service.IUserService;

@RestController
@RequestMapping("/manage/admin")
public class ManageUserAdmin {
	@Autowired 
	UserRepository userRepository;
	@Autowired
	IUserService iUserService;
	@Autowired
	ModelMapper mapper;
	@Autowired
	RoleRepository roleRepo;
	@GetMapping("/findAllUsers")
	public ResponseEntity<?> findAllUsers(@RequestParam(defaultValue = "0") int pageIndex,	@RequestParam(defaultValue = "12") int pageSize,@RequestParam(required = false) List<String> sortBy){
//		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
//			return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("chua dang nhap ma ba");
//		}
		Map<String, Object> listUser=iUserService.showListUser(pageIndex,pageSize);
		System.out.println(listUser);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "", listUser));
		
	}
	@PostMapping("/deleteUsers")
	public ResponseEntity<?> deletelistUser(@RequestParam("listId") Long [] ids){
//		this.iUserService.deleteUsers(ids);
//	this.userRepository.deleteOneById(id);
		try {
			this.iUserService.deleteIds(ids);

		} catch (Exception e) {
			System.out.println(e);
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "", "hihi"));
		
	}
	@PostMapping("userDetail/{id}")
	public ResponseEntity<?> userDetail(@PathVariable("id") Long id){
		System.out.println(id);
		UserEntity user=this.iUserService.findById(id);
		AdminUserResponse userResponse=mapper.map(user, AdminUserResponse.class);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "", userResponse));
	}
	@PostMapping("postUser")
	public ResponseEntity<?> postUser(@RequestBody UserReponse userResponse,@RequestParam("roleId") Long roleId){
		if(roleId==1) {
			UserEntity user=mapper.map(userResponse,UserEntity.class);
			RoleEntity role=roleRepo.findByName(ERole.ROLE_USER).get();
			Set<RoleEntity> set=new HashSet<RoleEntity>();
			user.setRoles(set);
			userRepository.save(user);
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "", userResponse));
	}
		
	}
	
	
	
	


