package com.example.chuyendeweb.repository;

import com.example.chuyendeweb.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long>, JpaSpecificationExecutor {
        Optional<ProductEntity> findById(Long id);
        Page<ProductEntity> findAll(Pageable pageable);
        Page<ProductEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
