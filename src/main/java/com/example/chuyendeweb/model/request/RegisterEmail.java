package com.example.chuyendeweb.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterEmail {
//    @NotBlank
    @Size(min = 6)
    private String name;
//    @NotBlank
    @Email(message = "Email không hợp lệ")
    private String email;
}
