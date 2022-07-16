package com.example.chuyendeweb.controller.user;

import com.example.chuyendeweb.model.response.FilterCategory;
import com.example.chuyendeweb.model.response.ResponseObject;
import com.example.chuyendeweb.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private ICategoryService iCategoryService;

    @GetMapping("/showCategory")
    public ResponseEntity<?> showCategory(@RequestParam (value = "nameCategory") String action){

        FilterCategory result = this.iCategoryService.filterCategory(action);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.value(),"successly show Category!",result));
    }
}
