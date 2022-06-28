package com.example.chuyendeweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.chuyendeweb.entity.ImageSell;

@Repository
public interface SellImageRepository extends JpaRepository<ImageSell, Long>{

}
