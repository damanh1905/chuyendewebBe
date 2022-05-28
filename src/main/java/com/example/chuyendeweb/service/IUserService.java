package com.example.chuyendeweb.service;

import com.example.chuyendeweb.entity.UserEntity;
import com.example.chuyendeweb.model.request.LogOutRequest;
import com.example.chuyendeweb.model.request.RegisterReq;
import com.example.chuyendeweb.model.request.ResetPasswordRequest;

import javax.mail.MessagingException;
import java.io.IOException;

public interface IUserService {
    UserEntity findById(Long id);

    String registerUser(RegisterReq RegisterReq) throws MessagingException, IOException;

    boolean verify(String verificationCode);

    boolean refeshVerifyCode(String email);

    boolean checkForgot(String email);


    boolean ResetPassword(ResetPasswordRequest resetPasswordRequest);

    boolean checklogout(LogOutRequest logOutRequest);
}
