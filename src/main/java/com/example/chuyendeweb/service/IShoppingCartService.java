package com.example.chuyendeweb.service;

import com.example.chuyendeweb.model.request.ChangeToCartReq;
import com.example.chuyendeweb.model.response.ChangeToCartResponse;
import com.example.chuyendeweb.security.CustomUserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


public interface IShoppingCartService {

    ChangeToCartResponse changeToCart(Long id, String action, ChangeToCartReq changeToCartReq);


    Set<ChangeToCartResponse> mergeToCart(CustomUserDetails userDetails, List<ChangeToCartReq> changeToCartReqList);


    Set<ChangeToCartResponse> showCart(CustomUserDetails userDetails);
}
