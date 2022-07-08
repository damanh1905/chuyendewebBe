package com.example.chuyendeweb.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.chuyendeweb.entity.OrderDetailEntity;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long>{

	List<OrderDetailEntity> findByOrderEntityId(Long id);

//	Page<OrderDetailEntity> findAllByOrderEntityId(Long id, Pageable pageable);

}
