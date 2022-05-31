package com.example.chuyendeweb.repository;

import com.example.chuyendeweb.entity.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {

        Optional<ProductEntity> findById(Long id);

        List<ProductEntity> findByNameContainingIgnoreCase( String name, Pageable pageable);
        Long countByNameContaining(String searchValue);
}
