package com.example.chuyendeweb.repository;

import com.example.chuyendeweb.entity.CartEntity;
import com.example.chuyendeweb.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;




@Repository
public interface CartRepository extends JpaRepository<CartEntity,Long> {
        @Query("SELECT c FROM CartEntity c where  c.userEntity = ?1")
        CartEntity findByLastModified(UserEntity user);
        CartEntity findByUserEntity(UserEntity userEntity);
}
