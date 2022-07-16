package com.example.chuyendeweb.controller.user;

import com.example.chuyendeweb.exception.NotFoundException;
import com.example.chuyendeweb.model.request.ChangeToCartReq;
import com.example.chuyendeweb.model.response.ChangeToCartResponse;
import com.example.chuyendeweb.model.response.ChangeToOrderResponse;
import com.example.chuyendeweb.model.response.ResponseObject;
import com.example.chuyendeweb.security.CustomUserDetails;
import com.example.chuyendeweb.service.IShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {

    @Autowired
    IShoppingCartService iShoppingCartService;

    @PostMapping("/addUpdateRemove")
    public ResponseEntity<?> changeToCart(@RequestParam(required = false) String action,
                                          @RequestBody(required = false) ChangeToCartReq changeToCartReq) {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            ChangeToCartResponse toCartResponse = iShoppingCartService.changeToCart(userDetails.getId(), action, changeToCartReq);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(HttpStatus.OK.value(), "Change cart successful!", toCartResponse));



    }
    @PostMapping("/merge")
    public ResponseEntity<?> mergeCart(@RequestBody List<ChangeToCartReq> changeToCartReqList){
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            throw new NotFoundException("please login to purchase!");
        }
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Set<ChangeToCartResponse> toCartResponseList = iShoppingCartService.mergeToCart(userDetails,changeToCartReqList);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(HttpStatus.OK.value(), "Change cart successful!", toCartResponseList));


    }
    @GetMapping("/listCart")
    public ResponseEntity<?> getShoppingCart(){
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
        	 System.out.println("AnonyMouse");
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            
        }
        System.out.println("List Cartttttt");
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ChangeToCartResponse> toCartResponseList =  iShoppingCartService.showCart(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(HttpStatus.OK.value(), "show list cart successful!", toCartResponseList));
    }
    //listorderCArt
	@GetMapping("/listOrder")
	public ResponseEntity<?> showListOrder() {
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<ChangeToOrderResponse> toOrderResponseList = iShoppingCartService.showOrder(userDetails);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject(HttpStatus.OK.value(), "show List Order successful!", toOrderResponseList));
	}
}
