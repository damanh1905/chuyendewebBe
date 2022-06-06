package com.example.chuyendeweb.repository.specification;

import com.example.chuyendeweb.entity.ProductEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ProductsSpecification implements Specification<ProductEntity> {

    private SearchCriteria searchCriteria;

    public ProductsSpecification(SearchCriteria searchCriteria) {
        super();
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<ProductEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (searchCriteria.getOperation().equalsIgnoreCase(">=")) {
            return builder.greaterThanOrEqualTo(
                    root.<String>get(searchCriteria.getKey()), searchCriteria.getValue().toString());
        }else if(searchCriteria.getOperation().equalsIgnoreCase("=<")){
            return builder.lessThanOrEqualTo(
                    root.<String> get(searchCriteria.getKey()),searchCriteria.getValue().toString());
        }else if(searchCriteria.getOperation().equalsIgnoreCase(":")){
            if(root.get(searchCriteria.getKey()).getJavaType()== String.class){
                return builder.like(
                        root.<String> get(searchCriteria.getKey()),"%" +searchCriteria.getValue()+ "%");
            }else{
                return builder.equal(
                        root.<String> get(searchCriteria.getKey()),searchCriteria.getValue());
            }
        }


        return null;
    }
}
