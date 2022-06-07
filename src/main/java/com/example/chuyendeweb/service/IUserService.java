package com.example.chuyendeweb.service;

import com.example.chuyendeweb.entity.UserEntity;
import com.example.chuyendeweb.model.request.*;

import javax.mail.MessagingException;
import java.io.IOException;

public interface IUserService {
    UserEntity findById(Long id);

    boolean finByUserName(String username);
    boolean finByEmail(String email);

    String registerUser(RegisterReq RegisterReq) throws MessagingException, IOException;

    boolean verify(int verificationCode);

    boolean refeshVerifyCode(String email);

    boolean checkForgot(String email);


    boolean ResetPassword(ResetPasswordRequest resetPasswordRequest);

    boolean checklogout(LogOutRequest logOutRequest);

    String registerEmail(RegisterEmail registerEmail) throws MessagingException, IOException;
}
