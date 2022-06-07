package com.example.chuyendeweb.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {

    private int verifyCodeForgot;
    @NotBlank
    @Min(value = 8, message = "Password phải từ 8 kí tự trở lên")
    private String newPassword;
    @NotBlank
    private String confirmPassword;
}
