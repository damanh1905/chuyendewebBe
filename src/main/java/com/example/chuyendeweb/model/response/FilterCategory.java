package com.example.chuyendeweb.model.response;

import com.example.chuyendeweb.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FilterCategory {
    private String NameCategory;
    private List<ProductResponse> productResponses ;
}
