package com.example.chuyendeweb;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.chuyendeweb.entity.UserEntity;
import com.example.chuyendeweb.model.response.UserReponse;
import com.example.chuyendeweb.repository.UserRepository;
import com.example.chuyendeweb.service.IUserService;

@SpringBootTest
class ChuyendewebBeApplicationTests {

    @Autowired
    IUserService iUserService;
    @Autowired
    UserRepository userpRepository;

    @Test
    void test1() {
        Optional<UserEntity> user = userpRepository.findByUserName("sa123456");
        UserReponse userReponse = new UserReponse(user.get().getUserName(), user.get().getEmail(),
                user.get().getPhone(), user.get().getGender());
        System.out.println(userReponse);
    }

    @Test
    void test2() {
        UserEntity userEntity = this.iUserService.finByName("sa123456");
        // userEntity.setEmail(userReponse.getEmail());
        // userEntity.setPhone(userReponse.getPhone());
        // userEntity.setGender(userReponse.getGender());
        System.out.println(userEntity);
    }
}
