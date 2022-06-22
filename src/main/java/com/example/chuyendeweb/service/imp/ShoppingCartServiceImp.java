package com.example.chuyendeweb.service.imp;

import com.example.chuyendeweb.entity.CartEntity;
import com.example.chuyendeweb.entity.CartItemEntity;
import com.example.chuyendeweb.entity.ProductEntity;
import com.example.chuyendeweb.entity.UserEntity;
import com.example.chuyendeweb.exception.NotFoundException;
import com.example.chuyendeweb.model.request.ChangeToCartReq;
import com.example.chuyendeweb.model.response.ChangeToCartResponse;
import com.example.chuyendeweb.model.response.ChangeToOrderResponse;
import com.example.chuyendeweb.repository.CartItemRepository;
import com.example.chuyendeweb.repository.CartRepository;
import com.example.chuyendeweb.repository.ProductRepository;
import com.example.chuyendeweb.security.CustomUserDetails;
import com.example.chuyendeweb.service.IShoppingCartService;
import com.example.chuyendeweb.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class ShoppingCartServiceImp implements IShoppingCartService {

    @Autowired
    IUserService iUserService;
    @Autowired
    CartRepository cartRespository;
    @Autowired
    CartItemRepository cartItemRespository;
    @Autowired
    ProductRepository productRespository;
    @Autowired
    ModelMapper mapper;
    @Override
    public ChangeToCartResponse changeToCart(Long id, String action, ChangeToCartReq changeToCartReq) {
        UserEntity userEntity = this.iUserService.findById(id);
        CartEntity foundCart = this.cartRespository.findByUserEntity(userEntity);
        if (foundCart == null) {
            foundCart = new CartEntity(new Date(),userEntity);
            this.cartRespository.save(foundCart);
        }
        ProductEntity updatingProduct =  this.productRespository.findById(changeToCartReq.getProductId()).get();
        if (updatingProduct == null) {
            throw  new NotFoundException("not found product");
        }
        int totalCartItem = updatingProduct.getPrice()* changeToCartReq.getQuantity();
        CartItemEntity updatingCartItemEntity = this.cartItemRespository.findByCartEntityAndProductEntities(foundCart,updatingProduct);
        if(!action.isEmpty()){
            switch (action){
                case "add":
                    if(updatingCartItemEntity != null){
                        totalCartItem = updatingCartItemEntity.getTotalPrice() +(updatingProduct.getPrice()* changeToCartReq.getQuantity());
                        updatingCartItemEntity.setQuantity(updatingCartItemEntity.getQuantity()+changeToCartReq.getQuantity());
                        updatingCartItemEntity.setTotalPrice(totalCartItem);
                    }else{
                        updatingCartItemEntity = new CartItemEntity(updatingProduct
                                ,changeToCartReq.getQuantity(),totalCartItem,foundCart);
                    }
                    this.cartItemRespository.saveAndFlush(updatingCartItemEntity);
                    break;
                case "change":
                        updatingCartItemEntity.setQuantity(changeToCartReq.getQuantity());
                        updatingCartItemEntity.setTotalPrice(totalCartItem);
                    this.cartItemRespository.saveAndFlush(updatingCartItemEntity);
                    break;
                case "remove":
                    this.cartItemRespository.deleteById(updatingCartItemEntity.getId());
                    break;
            }
        }
        ChangeToCartResponse toCartResponse = mapper.map(updatingCartItemEntity,ChangeToCartResponse.class);
        //System.out.println(toCartResponse);
        return toCartResponse;
    }

    @Override
    public Set<ChangeToCartResponse> mergeToCart(CustomUserDetails userDetails, List<ChangeToCartReq> changeToCartReqList) {
      UserEntity userEntity = iUserService.findById(userDetails.getId());
      CartEntity cartEntity = this.cartRespository.findByUserEntity(userEntity);
      if(cartEntity ==null ){
          cartEntity = new CartEntity(new Date(),userEntity);
          this.cartRespository.save(cartEntity);
      }
        for (ChangeToCartReq changeToCartReq:changeToCartReqList) {
            ProductEntity productEntity =  this.productRespository.findById(changeToCartReq.getProductId()).get();
            CartItemEntity foundCartItemEntity = this.cartItemRespository.findByCartEntityAndProductEntities(cartEntity,productEntity);
            if(foundCartItemEntity != null){
              int  totalCartItem = foundCartItemEntity.getTotalPrice() +(productEntity.getPrice()* changeToCartReq.getQuantity());
                int initalQuantity = foundCartItemEntity.getQuantity();
                foundCartItemEntity.setQuantity(initalQuantity+changeToCartReq.getQuantity());
                foundCartItemEntity.setTotalPrice(totalCartItem);

            }else{
                foundCartItemEntity = new CartItemEntity(productEntity,changeToCartReq.getQuantity()
                        ,productEntity.getPrice()* changeToCartReq.getQuantity(),cartEntity);
            }
            this.cartItemRespository.save(foundCartItemEntity);
        }
        Set<ChangeToCartResponse> result = new HashSet<>();
        List<CartItemEntity> cartItemEntities = cartEntity.getCartItemEntity();
        for (CartItemEntity cartItemEntity:cartItemEntities) {
            result.add(this.mapper.map(cartItemEntity,ChangeToCartResponse.class));

        }
        return result;
    }

    @Override
    public List<ChangeToCartResponse> showCart(CustomUserDetails userDetails) {
        UserEntity userEntity = iUserService.findById(userDetails.getId());
        CartEntity cartEntity = cartRespository.findByUserEntity(userEntity);
        if(cartEntity == null){
            cartEntity = new CartEntity(new Date(),userEntity);
            cartRespository.save(cartEntity);
        }

        List<CartItemEntity> cartItemEntities = cartEntity.getCartItemEntity();
        List<ChangeToCartResponse> result = new ArrayList<>();
        for (CartItemEntity cartItemEntity:cartItemEntities) {
            ChangeToCartResponse toCartResponse = this.mapper.map(cartItemEntity,ChangeToCartResponse.class);
            result.add(toCartResponse);
        }
        return result;
    }

	@Override
	public List<ChangeToOrderResponse> showOrder(CustomUserDetails userDetails) {
	       UserEntity userEntity = iUserService.findById(userDetails.getId());
	        CartEntity cartEntity = cartRespository.findByUserEntity(userEntity);
	        if(cartEntity == null){
	            //System.out.println("chưa có cart nên kh show Order");
	        }

	        List<CartItemEntity> cartItemEntities = cartEntity.getCartItemEntity();
	        List<ChangeToOrderResponse> result = new ArrayList<>();
	        for (CartItemEntity cartItemEntity:cartItemEntities) {
	        	ChangeToOrderResponse toCartResponse = this.mapper.map(cartItemEntity,ChangeToOrderResponse.class);
	            result.add(toCartResponse);
	        }
	        return result;
	}

}
