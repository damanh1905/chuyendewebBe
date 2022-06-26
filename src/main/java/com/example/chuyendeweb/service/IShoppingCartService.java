package com.example.chuyendeweb.service;

import com.example.chuyendeweb.model.request.ChangeToCartReq;
import com.example.chuyendeweb.model.response.ChangeToCartResponse;
import com.example.chuyendeweb.model.response.ChangeToOrderResponse;
import com.example.chuyendeweb.security.CustomUserDetails;

import java.util.List;
import java.util.Set;


public interface IShoppingCartService {

    ChangeToCartResponse changeToCart(Long id, String action, ChangeToCartReq changeToCartReq);

    Set<ChangeToCartResponse> mergeToCart(CustomUserDetails userDetails, List<ChangeToCartReq> changeToCartReqList);

    List<ChangeToCartResponse> showCart(CustomUserDetails userDetails);
    //xu li showOrder
    List<ChangeToOrderResponse> showOrder(CustomUserDetails userDetails);

}
