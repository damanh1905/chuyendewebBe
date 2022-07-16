package com.example.chuyendeweb.controller.user;

import java.util.Map;

import org.modelmapper.ModelMapper;
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

import com.example.chuyendeweb.entity.SellEntity;
import com.example.chuyendeweb.model.response.AdminSellResponse;
import com.example.chuyendeweb.model.response.ResponseObject;
import com.example.chuyendeweb.service.ISellService;

@RestController
@RequestMapping("/sell/admin")
public class ManageSellController {
	@Autowired
	ISellService iSellService;
	@Autowired 
	ModelMapper mapper;
	@GetMapping("showListSell")
	public ResponseEntity<?> showAllListSellAdmin(	@RequestParam(defaultValue = "0") int pageIndex, @RequestParam(defaultValue = "10") int pageSize){
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("bạn chưa đăng nhập");
		}
		Map<String, Object> listUser=iSellService.showListSellAdmin(pageIndex,pageSize);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "successfull", listUser));
	}
	
	@PostMapping("oneSell/{id}")
	public ResponseEntity<?> showOneSell(@PathVariable("id") Long id){
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("bạn chưa đăng nhập");
		}
		SellEntity sell=iSellService.findOneSell(id);
		AdminSellResponse adminSell=mapper.map(sell, AdminSellResponse.class);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "successfull", adminSell));
	}

}
