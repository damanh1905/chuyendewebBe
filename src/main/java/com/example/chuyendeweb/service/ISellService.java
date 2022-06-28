package com.example.chuyendeweb.service;

import java.util.List;

import com.example.chuyendeweb.entity.SellEntity;
import com.example.chuyendeweb.model.request.ChangeToSellReq;
import com.example.chuyendeweb.model.response.ChangeToListSellResponse;
import com.example.chuyendeweb.security.CustomUserDetails;

public interface ISellService {


	void saveSellUser(CustomUserDetails userDetails, ChangeToSellReq changeToSellReq);

	List<ChangeToListSellResponse> showListSell(CustomUserDetails userDetails);

	void deleteSellBySellId(Long sellId);

}
