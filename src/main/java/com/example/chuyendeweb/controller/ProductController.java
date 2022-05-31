package com.example.chuyendeweb.controller;


import com.example.chuyendeweb.model.response.PaginationResult;
import com.example.chuyendeweb.model.response.ProductResponse;
import com.example.chuyendeweb.model.response.ResponseObject;
import com.example.chuyendeweb.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*", maxAge = 360)
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
    @GetMapping("/search")
    public ResponseEntity<?> searchProductEntity(@RequestParam(required = true) String searchValue,
           @RequestParam int pageIndex,  @RequestParam int pageSize){
        Pageable pageable = PageRequest.of(pageIndex,pageSize);
        List<ProductResponse> responseList;
        responseList = this.iProductService.searchProduct(searchValue,pageable);
        PaginationResult<ProductResponse> result = new PaginationResult<>();
        result.setItems(responseList);
        result.setTotalLength(this.iProductService.getLength(searchValue));
        return ResponseEntity.ok(result);

    }
}
