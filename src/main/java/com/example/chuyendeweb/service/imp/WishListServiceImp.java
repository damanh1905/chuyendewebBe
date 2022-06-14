package com.example.chuyendeweb.service.imp;

import com.example.chuyendeweb.entity.ProductEntity;
import com.example.chuyendeweb.entity.UserEntity;
import com.example.chuyendeweb.entity.WishListEntity;
import com.example.chuyendeweb.entity.WishListItemEntity;
import com.example.chuyendeweb.repository.ProductRepository;
import com.example.chuyendeweb.repository.UserRepository;
import com.example.chuyendeweb.repository.WishListItemRepository;
import com.example.chuyendeweb.repository.WishListRepository;
import com.example.chuyendeweb.service.IWishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class WishListServiceImp implements IWishListService {
    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    WishListItemRepository wishListItemRepository;

    @Override
    public ProductEntity addProductWishList(Long id, Long id1) {
        UserEntity user = this.userRepository.findById(id).get();
        WishListEntity wishList = this.wishListRepository.findByUserEntity(user);
        if (wishList == null) {
            wishList = new WishListEntity(user);
            this.wishListRepository.save(wishList);
        }
        ProductEntity productEntity = this.productRepository.findById(id1).get();
        WishListItemEntity wishListItemEntity = this.wishListItemRepository.findByWishListAndProductEntities(wishList, productEntity);
        if (wishListItemEntity != null) {
            return null;
        } else {
            wishListItemEntity = new WishListItemEntity(productEntity, wishList);
            this.wishListItemRepository.save(wishListItemEntity);
        }
        return productEntity;
    }

    @Override
    public List<WishListItemEntity> findAll(Specification<WishListItemEntity> spec, Pageable pageable) {
        return this.wishListItemRepository.findAll(spec,pageable).getContent();
    }

    @Override
    public WishListEntity findByUserEntity(UserEntity userEntity) {
        return this.wishListRepository.findByUserEntity(userEntity);
    }

    @Override
    public void delete(ProductEntity productEntity) {
        this.wishListItemRepository.deleteByProductEntities(productEntity);

    }
}
