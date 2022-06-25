package com.example.chuyendeweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.chuyendeweb.entity.OrderEntity;
import com.example.chuyendeweb.entity.UserEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>{
	OrderEntity findByUserEntity(UserEntity userEntity);

	List<OrderEntity> findByUserEntityId(Long id);
}
