package com.example.chuyendeweb.repository;

import com.example.chuyendeweb.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {

    List<OrderDetailEntity> findByOrderEntityId(Long id);

    //	Page<OrderDetailEntity> findAllByOrderEntityId(Long id, Pageable pageable);
    @Query(
            value = "SELECT * FROM orderDetail o where MONTH(dateCreated) = ?1 AND YEAR(dateCreated) = ?2",
            nativeQuery = true)
    List<OrderDetailEntity> findAllByDateCreated(int month, int year);
}
