package com.example.chuyendeweb.repository;

import com.example.chuyendeweb.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {

        Optional<ProductEntity> findById(Long id);
}
