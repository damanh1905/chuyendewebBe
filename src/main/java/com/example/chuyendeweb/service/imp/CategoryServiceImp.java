package com.example.chuyendeweb.service.imp;

import com.example.chuyendeweb.entity.CategoryEntity;
import com.example.chuyendeweb.entity.ProductEntity;
import com.example.chuyendeweb.model.response.FilterCategory;
import com.example.chuyendeweb.model.response.ProductResponse;
import com.example.chuyendeweb.repository.CategoryRepository;
import com.example.chuyendeweb.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImp implements ICategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ModelMapper mapper;

    @Override
    public FilterCategory filterCategory(String action) {
        FilterCategory filterCategory = new FilterCategory();
        if (!StringUtils.isEmpty(action)) {
            CategoryEntity entityProducts = this.categoryRepository.findByNameCategory(action);
            filterCategory = this.covertCategoryEntityToResponse(entityProducts.getProductEntitys(), action);
        }
        return filterCategory;
    }

    public FilterCategory covertCategoryEntityToResponse(List<ProductEntity> productDetailEntities, String action) {
        List<ProductResponse> responseList = new ArrayList<>();
        for (ProductEntity productEntity : productDetailEntities) {
            responseList.add(this.mapper.map(productEntity, ProductResponse.class));
         //System.out.println(productDetailEntities);
        }
        FilterCategory filterCategory = new FilterCategory();
        filterCategory.setNameCategory(action);
        filterCategory.setProductResponses(responseList);
        return filterCategory;
    }
}
