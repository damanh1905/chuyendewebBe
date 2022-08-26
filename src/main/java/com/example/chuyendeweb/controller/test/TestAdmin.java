package com.example.chuyendeweb.controller.test;

import com.example.chuyendeweb.entity.CategoryEntity;
import com.example.chuyendeweb.entity.OrderEntity;
import com.example.chuyendeweb.entity.ProductEntity;
import com.example.chuyendeweb.entity.UserEntity;
import com.example.chuyendeweb.model.response.ProductResponse;
import com.example.chuyendeweb.model.response.ResponseObject;
import com.example.chuyendeweb.repository.CategoryRepository;
import com.example.chuyendeweb.repository.OrderRepository;
import com.example.chuyendeweb.repository.ProductRepository;
import com.example.chuyendeweb.repository.UserRepository;
import com.example.chuyendeweb.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
@RestController
public class TestAdmin {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    IProductService iProductService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;
    @GetMapping("/all")
    public ResponseEntity<?> test(@RequestParam int month) throws ParseException {
//        Date date = new Date(2022-07-10);
//
//
//        List<OrderEntity> entityList = orderRepository.findAllByDateCreated( new SimpleDateFormat("yyyy-MM").parse("2022-"+month));
//        if(entityList != null ){
//            System.out.println("aaaaa");
//            return ResponseEntity.status(HttpStatus.OK)
//                    .body(new ResponseObject(HttpStatus.OK.value(), "show listOrderByUser successful!", entityList));
//
//        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("s");
    }

    @GetMapping("/admin")
//    @PreAuthorize("hasRole('MODERATOR')")
    public ProductEntity adminAccess() {
        long a=2;
        return this.productRepository.findById(a).get() ;
    }
}
