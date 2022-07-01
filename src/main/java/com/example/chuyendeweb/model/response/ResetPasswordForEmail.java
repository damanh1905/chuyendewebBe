package com.example.chuyendeweb.model.response;

public class ResetPasswordForEmail {

    String email;
    String password;

    public ResetPasswordForEmail(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ResetEmail [email=" + email + ", password=" + password + "]";
    }

}
