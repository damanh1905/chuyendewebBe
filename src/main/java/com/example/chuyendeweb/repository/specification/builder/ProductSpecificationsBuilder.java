package com.example.chuyendeweb.repository.specification.builder;

import com.example.chuyendeweb.entity.ProductEntity;
import com.example.chuyendeweb.repository.specification.ProductsSpecification;
import com.example.chuyendeweb.repository.specification.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductSpecificationsBuilder {
    private final List<SearchCriteria> params;

    public ProductSpecificationsBuilder() {
        this.params = new ArrayList<SearchCriteria>();
    }
    public ProductSpecificationsBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }
    public Specification<ProductEntity> build(){
        if(params.size() == 0 ){
            return null;
        }
        List<Specification> specs = params.stream().map(ProductsSpecification::new).collect(Collectors.toList());
        Specification result =  specs.get(0);
        for (int i = 0; i < params.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
            
        }
        return result;
    }
}
