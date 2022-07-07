package com.example.chuyendeweb.model.response;

import java.util.HashSet;
import java.util.Set;

import com.example.chuyendeweb.entity.RoleEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminUserResponse {
    String userName;
    String email;
    String phone;
    String gender;
    private String statuss;
    private Set<RoleEntity> roles = new HashSet<>();
    

}
