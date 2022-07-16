package com.example.chuyendeweb.controller.user;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RestController;

import com.example.chuyendeweb.entity.SellEntity;
import com.example.chuyendeweb.exception.NotFoundException;
import com.example.chuyendeweb.model.request.ChangeToSellReq;
import com.example.chuyendeweb.model.response.ChangeToListSellResponse;
import com.example.chuyendeweb.model.response.ResponseObject;
import com.example.chuyendeweb.security.CustomUserDetails;
import com.example.chuyendeweb.service.ISellService;

@RestController
@RequestMapping("api/sell")

public class SellController {
	@Autowired
	ISellService sellService;
	@PostMapping("postSell")
	public ResponseEntity<?> postSell(@RequestBody ChangeToSellReq changeToSellReq) throws IOException{
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			  return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
//		System.out.println(userDetails);
		

	
		
		sellService.saveSellUser(userDetails,changeToSellReq);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "show Sell successful!", new Integer(3)));
	}
	@GetMapping("listSell")
	public ResponseEntity<?> listSell(){
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<ChangeToListSellResponse> listSell=sellService.showListSell(userDetails);
		System.out.println(listSell.toString());
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "show Sell successful!", listSell));
	}
	@PostMapping("deleteSell/{sellId}")
	public ResponseEntity<?> deleteSellId(@PathVariable(name = "sellId", required = true) Long sellId){
				sellService.deleteSellBySellId(sellId);
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseObject(HttpStatus.OK.value(), "delete Sell successful!", new Integer(3)));
	}
	

}
