package com.example.chuyendeweb.service.imp;

import com.example.chuyendeweb.entity.CategoryEntity;
import com.example.chuyendeweb.entity.ProductEntity;
import com.example.chuyendeweb.exception.NotFoundException;
import com.example.chuyendeweb.model.response.CategoryResponse;
import com.example.chuyendeweb.model.response.ProductResponse;
import com.example.chuyendeweb.repository.CategoryRepository;
import com.example.chuyendeweb.repository.ProductRepository;
import com.example.chuyendeweb.service.IProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        ProductEntity productEntity = productRepository.findById(productId).get();
        CategoryEntity categoryEntity = categoryRepository.findByProductEntitys(productEntity);
        CategoryResponse categoryResponse = this.mapper.map(categoryEntity,CategoryResponse.class);
        ProductResponse productResponse = this.mapper.map(productEntity,ProductResponse.class);
        productResponse.setCategoryResponse(categoryResponse);
        return productResponse ;
    }

    @Override
    public List<ProductResponse> searchProduct(String searchValue,Pageable pageable) {
        List<ProductEntity> list = this.productRepository.findByNameContainingIgnoreCase(searchValue,pageable);
        System.out.println(list.toString());
        return this.covertProductDetailToEntity(list);
    }
    public List<ProductResponse> covertProductDetailToEntity(List<ProductEntity> productDetailEntities){
                List<ProductResponse> responseList = new ArrayList<>();
//        if(productDetailEntities.size() <1){
//            throw  new NotFoundException("not found product");
//        }
        for (ProductEntity productEntity :productDetailEntities) {

            responseList.add(this.mapper.map(productEntity,ProductResponse.class));
            System.out.println(productDetailEntities);

        }
        return  responseList;
    }
    @Override
    public Long getLength(String searchValue) {
        return this.productRepository.countByNameContaining(searchValue);
    }
}
