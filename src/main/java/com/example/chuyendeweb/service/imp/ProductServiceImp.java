package com.example.chuyendeweb.service.imp;

import com.example.chuyendeweb.entity.CategoryEntity;
import com.example.chuyendeweb.entity.ProductEntity;
import com.example.chuyendeweb.model.response.CategoryResponse;
import com.example.chuyendeweb.model.response.ProductResponse;
import com.example.chuyendeweb.repository.CategoryRepository;
import com.example.chuyendeweb.repository.ProductRepository;
import com.example.chuyendeweb.service.IProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImp implements IProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ModelMapper mapper;
    @Override
    public ProductResponse findById(Long productId) {
        ProductEntity productEntity =  productRepository.findById(productId).get();
        CategoryEntity categoryEntity = categoryRepository.findByProductEntitys(productEntity);
        CategoryResponse categoryResponse = this.mapper.map(categoryEntity,CategoryResponse.class);
        ProductResponse productResponse = this.mapper.map(productEntity,ProductResponse.class);
        productResponse.setCategoryResponse(categoryResponse);
        return productResponse ;
    }

    @Override
    public Map<String ,Object> showAndSearchProduct(String searchValue, Pageable pageable) {

        Page<ProductEntity> pageTuts;
        if(searchValue == null) {
            pageTuts = this.productRepository.findAll(pageable);
        }else {
            pageTuts = this.productRepository.findByNameContainingIgnoreCase(searchValue, pageable);
        }
        List<ProductEntity> productEntityList = pageTuts.getContent();
        List<ProductResponse> ProductResponse = this.covertProductDetailToEntity(productEntityList);
        Map<String,Object> result = new HashMap<>();
        result.put("products",ProductResponse);
        result.put("curerentPage",pageTuts.getNumber());
        result.put("totalitems",pageTuts.getTotalElements());
        result.put("totalPage",pageTuts.getTotalPages());
        return result ;
    }
    public List<ProductResponse> covertProductDetailToEntity(List<ProductEntity> productDetailEntities){
                List<ProductResponse> responseList = new ArrayList<>();
        for (ProductEntity productEntity :productDetailEntities) {

            responseList.add(this.mapper.map(productEntity,ProductResponse.class));
            System.out.println(productDetailEntities);

        }
        return  responseList;
    }



}
