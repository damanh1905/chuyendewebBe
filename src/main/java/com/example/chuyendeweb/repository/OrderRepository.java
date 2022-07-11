package com.example.chuyendeweb.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.chuyendeweb.entity.OrderEntity;
import com.example.chuyendeweb.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>{
	OrderEntity findByUserEntity(UserEntity userEntity);

	List<OrderEntity> findByUserEntityId(Long id);
	@Transactional
	void deleteOneById(Long orderId);

	OrderEntity findOneById(Long id);
@Query(
		value = "SELECT * FROM orderEntity o where MONTH(dateCreated) = ?1 AND YEAR(dateCreated) = ?2",
		nativeQuery = true)
	List<OrderEntity> findAllByDateCreated(int month,int year);
	List<OrderEntity> findAllByDateCreated(Date publicationDate);
}
