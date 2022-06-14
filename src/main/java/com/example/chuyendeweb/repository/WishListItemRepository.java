package com.example.chuyendeweb.repository;

import com.example.chuyendeweb.entity.ProductEntity;
import com.example.chuyendeweb.entity.WishListEntity;
import com.example.chuyendeweb.entity.WishListItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface WishListItemRepository extends JpaRepository<WishListItemEntity, Long>, JpaSpecificationExecutor<WishListItemEntity> {
    WishListItemEntity findByWishListAndProductEntities(WishListEntity wishList, ProductEntity productEntity);

    Page findAll(Specification spec, Pageable pageable);
    @Transactional
    void deleteByProductEntities(ProductEntity productEntity);
}
