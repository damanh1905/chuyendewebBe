package com.example.chuyendeweb.model.response;

public class UserReponse {
    String userName;
    String email;
    String address;
    String phone;
    String gender;

    public UserReponse(String userName, String email, String address, String phone, String gender) {
        this.userName = userName;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.gender = gender;
    }

}