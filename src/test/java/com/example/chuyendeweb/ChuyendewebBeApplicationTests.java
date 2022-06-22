package com.example.chuyendeweb;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.chuyendeweb.entity.UserEntity;
import com.example.chuyendeweb.model.response.UserReponse;
import com.example.chuyendeweb.repository.UserRepository;

@SpringBootTest
class ChuyendewebBeApplicationTests {

    @Autowired
    UserRepository userpRepository;

    @Test
    void contextLoads() {
        Optional<UserEntity> user = userpRepository.findByUserName("sa123456");
        UserReponse userReponse = new UserReponse(user.get().getUserName(), user.get().getEmail(),
                        user.get().getAddress(), user.get().getPhone(), user.get().getGender());
                        //System.out.println(userReponse);
    }

}
