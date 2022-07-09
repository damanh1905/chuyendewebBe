package com.example.chuyendeweb.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.chuyendeweb.entity.ImageSell;
import com.example.chuyendeweb.entity.OrderEntity;
import com.example.chuyendeweb.entity.SellEntity;
import com.example.chuyendeweb.entity.UserEntity;
import com.example.chuyendeweb.model.request.ChangeToSellReq;
import com.example.chuyendeweb.model.response.AdminSellResponse;
import com.example.chuyendeweb.model.response.ChangeToListSellResponse;
import com.example.chuyendeweb.model.response.ChangeToOrderResponseByUser;
import com.example.chuyendeweb.repository.SellImageRepository;
import com.example.chuyendeweb.repository.SellRepository;
import com.example.chuyendeweb.repository.UserRepository;
import com.example.chuyendeweb.security.CustomUserDetails;
import com.example.chuyendeweb.service.ISellService;
import com.example.chuyendeweb.service.IUserService;

@Service
public class SellIml implements ISellService{
	@Autowired
	SellRepository sellRepository;
	@Autowired
	SellImageRepository sellImageRepository;
    @Autowired
    IUserService iUserService;
    @Autowired
    UserRepository userRepo;
    @Autowired
    ModelMapper mapper;
	@Override
	public void saveSellUser(CustomUserDetails userDetails, ChangeToSellReq changeToSellReq) {
	    UserEntity userEntity = this.iUserService.findById(userDetails.getId());
//				SellEntity sell=new SellEntity(email, nameProduct, price, size, brand, description, note, imageSell, userEntity)
	    	SellEntity sell=new SellEntity();
	    	sell.setEmail(changeToSellReq.getEmail());
	    	sell.setNameProduct(changeToSellReq.getName());
	    	sell.setPrice(changeToSellReq.getPrice());
	    	sell.setBrand(changeToSellReq.getBrand());
	    	sell.setDescription(changeToSellReq.getDescription());
	    	sell.setNote(changeToSellReq.getNote());
	    	sell.setSize(changeToSellReq.getSize());
	    	sell.setDateCreated(new Date());
	    	sell.setUserEntity(userEntity);
	    	sellRepository.save(sell);
	    	for (int i = 0; i < changeToSellReq.getUpload().length; i++) {
						ImageSell imgSell=new ImageSell();
						imgSell.setBase64(changeToSellReq.getUpload()[i]);
						imgSell.setSellEntity(sell);
						imgSell.setDateCreated(new Date());
						sellImageRepository.save(imgSell);
			}
	}
	@Override
	public List<ChangeToListSellResponse> showListSell(CustomUserDetails userDetails) {
		 UserEntity user = userRepo.findOnedById(userDetails.getId());
		 List<SellEntity> listSellEntity=sellRepository.findByUserEntityId(user.getId());
		 System.out.println("helo"+listSellEntity);
		 List<ChangeToListSellResponse> result = new ArrayList<>();
		 for (SellEntity sell : listSellEntity) {
	            result.add(this.mapper.map(sell, ChangeToListSellResponse.class));
	        }
		return result;
	}
	@Override
	public void deleteSellBySellId(Long sellId) {
		sellRepository.deleteOneById(sellId);
		
	}
	@Override
	public Map<String, Object> showListSellAdmin(int pageIndex, int pageSize) {
		Map<String, Object> result=new HashMap<String, Object>();
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<SellEntity> pageTuts;
		pageTuts=sellRepository.findAll(pageable);
		List<SellEntity> listSell=pageTuts.getContent();
		List<AdminSellResponse> listResponse=new ArrayList<AdminSellResponse>();
		for (SellEntity sellEntity : listSell) {
			listResponse.add(mapper.map(sellEntity, AdminSellResponse.class));
		}
		result.put("listSell", listResponse);
	
		return result;
		
	}
	@Override
	public SellEntity findOneSell(Long id) {
			SellEntity sell=sellRepository.findById(id).get();
		return sell;
	}

}
