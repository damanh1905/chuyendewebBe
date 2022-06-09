package com.example.chuyendeweb.controller;


import com.example.chuyendeweb.model.response.ProductResponse;
import com.example.chuyendeweb.model.response.ResponseObject;
import com.example.chuyendeweb.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
//@CrossOrigin(origins = "*", maxAge = 360)
@RequestMapping("api/product")
    public class ProductController {
    @Autowired
    IProductService iProductService;

    @GetMapping("/product-detail/{productId}")
    public ResponseEntity<?> showProductDetail(@PathVariable(name = "productId", required = true) Long productId) {
        ProductResponse result = this.iProductService.findById(productId);
        if (result == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(HttpStatus.NOT_FOUND.value(), "Not found product", ""));

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.value(), "product detail ", result));

    }
//    @PreAuthorize("hasRole('USER')")
    @GetMapping("/ShowAndsearch")
    public ResponseEntity<?> showAndsearchProductEntity(@RequestParam(required = true) String searchValue,
           @RequestParam(defaultValue = "0") int pageIndex,  @RequestParam(defaultValue = "10") int pageSize){

            Pageable pageable = PageRequest.of(pageIndex,pageSize);
            Map<String,Object> result= this.iProductService.showAndSearchProduct(searchValue,pageable);
            if(result.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(HttpStatus.NOT_FOUND.value(), "no product in db", ""));
            }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.value(), "successly!", result));




    }
}
