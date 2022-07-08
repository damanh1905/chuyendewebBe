package com.example.chuyendeweb.model.response;

import java.util.Date;
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
	private Long id;
    private String userName;
    private String email;
    private String phone;
    private String gender;
    private String statuss;
    private Date dateCreated;
    private int totalOrder;
    private long totalPrice;
    
    

}
