package com.example.chuyendeweb.service;

import com.example.chuyendeweb.entity.UserEntity;
import com.example.chuyendeweb.model.request.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Map;

public interface IUserService {
    UserEntity findById(Long id);

    UserEntity finByName(String username);

    boolean finByUserName(String username);

    boolean finByEmail(String email);

    String registerUser(RegisterReq RegisterReq) throws MessagingException, IOException;

    boolean verify(int verificationCode);

    boolean refeshVerifyCode(String email);

    boolean checkForgot(String email);

    boolean verifyForgot(int verificationCode);

    boolean ResetPassword(ResetPasswordRequest resetPasswordRequest);

    boolean checklogout(String userName);

    String registerEmail(RegisterEmail registerEmail) throws MessagingException, IOException;

    UserEntity saveAndFlush(UserEntity user);

    UserEntity setUserToGG(String username, String email);

	Map<String, Object> showListUser(int pageIndex, int pageSize);


	void deleteIds(Long[] ids);

}
