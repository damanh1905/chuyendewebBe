package com.example.chuyendeweb.controller;

import com.example.chuyendeweb.entity.ProductEntity;
import com.example.chuyendeweb.exception.NotFoundException;
import com.example.chuyendeweb.model.response.ChangeToCartResponse;
import com.example.chuyendeweb.model.response.ResponseObject;
import com.example.chuyendeweb.security.CustomUserDetails;
import com.example.chuyendeweb.service.IWishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/wishlist")
public class WishListController {
    @Autowired
    IWishListService iWishListService;
    @PostMapping("/addWishList")
    public ResponseEntity<?> addWishList(@RequestParam (value =  "iDProduct" ) Long id ) {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            throw new NotFoundException("please login to purchase!");
        }
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ProductEntity toCartResponse = iWishListService.addProductWishList(userDetails.getId(),id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(HttpStatus.OK.value(), "Change cart successful!", toCartResponse));

    }
}
