package com.example.chuyendeweb.repository.specification;

import com.example.chuyendeweb.entity.ProductEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;

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

        }if (searchCriteria.getOperation().equalsIgnoreCase("=")) {
            return builder.equal(root.<String>get(searchCriteria.getKey()), searchCriteria.getValue());
        }
        else if(searchCriteria.getOperation().equalsIgnoreCase("=<")){
            return builder.lessThanOrEqualTo(
                    root.<String> get(searchCriteria.getKey()),searchCriteria.getValue().toString());
        } else if (searchCriteria.getOperation().equalsIgnoreCase("in")) {
        	if(searchCriteria.getKey().equals("sourceOrigin")) {
        		System.out.println("chanvl");
        		return builder.in(root.get(searchCriteria.getKey())).value(searchCriteria.getValue());
        	}else {
        	     Join<ProductEntity, Object> join = root.join(searchCriteria.getKey());
                 query.distinct(true);
                 return builder.in(join.get("id")).value(searchCriteria.getValue());
        	}
       
        }else if(searchCriteria.getOperation().equalsIgnoreCase("in2")) {
        	
        }		
        else if (searchCriteria.getOperation().equalsIgnoreCase("between")) {
            List<Long> priceRange = (List<Long>) searchCriteria.getValue();
            System.out.println("size"+priceRange.size());
            System.out.println("value"+priceRange.toString());
            long min =  priceRange.get(0);
            long max =  priceRange.get(1);
            return builder.between(root.get(searchCriteria.getKey()), min, max);
        }
        else if(searchCriteria.getOperation().equalsIgnoreCase(":")){
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
