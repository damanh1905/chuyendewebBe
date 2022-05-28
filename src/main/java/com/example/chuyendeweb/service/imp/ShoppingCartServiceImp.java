package com.example.chuyendeweb.service.imp;

import com.example.chuyendeweb.entity.CartEntity;
import com.example.chuyendeweb.entity.CartItemEntity;
import com.example.chuyendeweb.entity.ProductEntity;
import com.example.chuyendeweb.entity.UserEntity;
import com.example.chuyendeweb.exception.NotFoundException;
import com.example.chuyendeweb.model.request.ChangeToCartReq;
import com.example.chuyendeweb.model.response.ChangeToCartResponse;
import com.example.chuyendeweb.repository.CartItemRespository;
import com.example.chuyendeweb.repository.CartRespository;
import com.example.chuyendeweb.repository.ProductRespository;
import com.example.chuyendeweb.security.CustomUserDetails;
import com.example.chuyendeweb.service.IShoppingCartService;
import com.example.chuyendeweb.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ShoppingCartServiceImp implements IShoppingCartService {

    @Autowired
    IUserService iUserService;
    @Autowired
    CartRespository cartRespository;
    @Autowired
    CartItemRespository cartItemRespository;
    @Autowired
    ProductRespository productRespository;
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
        ProductEntity updatingProduct = this.productRespository.findById(changeToCartReq.getProductId()).get();
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
                case "remove":
                    this.cartItemRespository.delete(updatingCartItemEntity);
                    break;
            }
        }
        ChangeToCartResponse toCartResponse = mapper.map(updatingCartItemEntity,ChangeToCartResponse.class);
        System.out.println(toCartResponse);
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
            ProductEntity productEntity = this.productRespository.findById(changeToCartReq.getProductId()).get();
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
        Set<CartItemEntity> cartItemEntities = cartEntity.getCartItemEntity();
        for (CartItemEntity cartItemEntity:cartItemEntities) {
            result.add(this.mapper.map(cartItemEntity,ChangeToCartResponse.class));

        }
        return result;
    }

    @Override
    public Set<ChangeToCartResponse> showCart(CustomUserDetails userDetails) {
        return null;
    }


}
