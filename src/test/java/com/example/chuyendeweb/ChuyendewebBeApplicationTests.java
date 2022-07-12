package com.example.chuyendeweb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.chuyendeweb.entity.AgesEntity;
import com.example.chuyendeweb.entity.CartItemEntity;
import com.example.chuyendeweb.entity.CategoryEntity;
import com.example.chuyendeweb.entity.ImageEntity;
import com.example.chuyendeweb.entity.ProductEntity;
import com.example.chuyendeweb.entity.UserEntity;
import com.example.chuyendeweb.model.response.ProductResponse;
import com.example.chuyendeweb.model.response.UserReponse;
import com.example.chuyendeweb.repository.ProductRepository;
import com.example.chuyendeweb.repository.UserRepository;
import com.example.chuyendeweb.service.IProductService;
import com.example.chuyendeweb.service.IUserService;

@SpringBootTest
class ChuyendewebBeApplicationTests {

    @Autowired
    IUserService iUserService;
    @Autowired
    UserRepository userpRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    IProductService iProductService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ModelMapper mapper;

    @Test
    void test1() {
        Optional<UserEntity> user = userpRepository.findByUserName("sa123456");
        UserReponse userReponse = new UserReponse(user.get().getUserName(), user.get().getEmail(),
                user.get().getPhone(), user.get().getGender());
        System.out.println(userReponse);
    }

    @Test
    void test2() {
        // UserEntity userEntity = this.iUserService.finByName("sa123456");
        UserEntity userEntity = userpRepository.findByEmail("jackquaytay@gmail.com");
        // userEntity.setEmail(userReponse.getEmail());
        // userEntity.setPhone(userReponse.getPhone());
        // userEntity.setGender(userReponse.getGender());
        System.out.println(userEntity);
    }

    @Test
    void test3() {
        UserEntity userEntity = userpRepository.findByEmail("jackquaytay@gmail.com");
        userEntity.setPasswords(encoder.encode("1234567890"));
        userpRepository.save(userEntity);
        System.out.println(userEntity);
    }

    @Test
    void test4() {

        List<ProductEntity> listEntity = this.productRepository.findAll();
        List<ProductResponse> result = this.iProductService.getRandomProduct(listEntity, 51);

        // List<ProductResponse> result = new ArrayList<>();
        // for (ProductEntity productEntity : listEntity) {
        // System.out.println(this.iProductService.findById(productEntity.getId()));
        // result.add(this.iProductService.findById(productEntity.getId()));
        // }
        // List<ProductEntity> result =
        // this.iProductService.getRandomProduct(this.productRepository.findAll(), 51);

        // List<ProductResponse> result =
        // this.iProductService.covertProductEntityToResponse(listEntity);

        // List<ProductResponse> responseList = new ArrayList<>();
        // for (ProductEntity productEntity : listEntity) {
        // System.out.println(productEntity);
        // responseList.add(this.mapper.map(productEntity, ProductResponse.class));
        // }
        System.out.println(result);
    }

    @Test
    void test5() {
        ProductEntity result = this.iProductService.findByIdProduct(Long.valueOf(51));

        result.setName("abc");
        result.setAmount(10000);
        result.setDescriptions("setDescriptions");
        result.setPrice(1233000000);
        result.setPrice_Sale(12330);
        result.setSourceOrigin("sourceOrigin");

        Set<AgesEntity> agesEntities = null;
        result.setAges(agesEntities);
        List<ImageEntity> imageEntity = new ArrayList<>();
        result.setImageEntity(imageEntity);
        List<CartItemEntity> cartItemEntity = new ArrayList<>();
        result.setCartItemEntity(cartItemEntity);
        result.setCategoryEntity(new CategoryEntity());

        productRepository.save(result);
        // productRepository.delete(result);
        // System.out.println(result);
    }


}
