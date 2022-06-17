package com.example.chuyendeweb.repository;


import com.example.chuyendeweb.entity.UserEntity;
import com.example.chuyendeweb.entity.WishListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends JpaRepository<WishListEntity, Long> , JpaSpecificationExecutor {
    WishListEntity findByUserEntity(UserEntity userEntity);
}
