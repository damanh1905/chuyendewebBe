package com.example.chuyendeweb.model.response;


import com.example.chuyendeweb.entity.ImageEntity;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private int Price;
    private int Price_Sale;
    private int amount;
    private boolean isNew;
    private String sourceOrigin;
    private String name;
    private String descriptions;
    private CategoryResponse categoryResponse;
    private List<ImageResponse> ImageEntity;

}
