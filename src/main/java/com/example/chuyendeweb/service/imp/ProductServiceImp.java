package com.example.chuyendeweb.service.imp;

import com.example.chuyendeweb.entity.CategoryEntity;
import com.example.chuyendeweb.entity.ProductDetailEntity;
import com.example.chuyendeweb.entity.ProductEntity;
import com.example.chuyendeweb.model.response.CategoryResponse;
import com.example.chuyendeweb.model.response.ProductDetailResponse;
import com.example.chuyendeweb.model.response.ProductResponse;
import com.example.chuyendeweb.repository.CategoryRepository;
import com.example.chuyendeweb.repository.ProductDetailRepository;
import com.example.chuyendeweb.repository.ProductRepository;
import com.example.chuyendeweb.service.IProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImp implements IProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductDetailRepository productDetailRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ModelMapper mapper;
    @Override
    public ProductResponse findById(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId).get();
        ProductDetailEntity productDetailEntity =  productDetailRepository.findByProductEntity(productEntity);
        CategoryEntity categoryEntity = categoryRepository.findByProductEntitys(productEntity);
        CategoryResponse categoryResponse = this.mapper.map(categoryEntity,CategoryResponse.class);
        ProductDetailResponse productDetailResponse = this.mapper.map(productDetailEntity,ProductDetailResponse.class);
        ProductResponse productResponse = this.mapper.map(productEntity,ProductResponse.class);
        productResponse.setProductDetailResponse(productDetailResponse);
        productResponse.setCategoryResponse(categoryResponse);
        return productResponse ;
    }
}
