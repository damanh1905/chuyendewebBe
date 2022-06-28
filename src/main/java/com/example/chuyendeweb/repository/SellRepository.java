package com.example.chuyendeweb.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.chuyendeweb.entity.SellEntity;

@Repository
public interface SellRepository extends JpaRepository<SellEntity, Long>{

	List<SellEntity> findByUserEntityId(Long id);

	@Transactional
	void deleteOneById(Long sellId);

}
