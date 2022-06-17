package com.example.chuyendeweb.controller;

import com.example.chuyendeweb.entity.RefreshTokenEntity;
import com.example.chuyendeweb.entity.UserEntity;
import com.example.chuyendeweb.model.response.JwtResponse;
import com.example.chuyendeweb.security.CustomUserDetails;
import com.example.chuyendeweb.security.RefreshTokenService;
import com.example.chuyendeweb.service.IUserService;
import com.example.chuyendeweb.util.JwtUtils;
import com.example.chuyendeweb.util.SendEmailUtils;
import com.example.chuyendeweb.util.SiteUrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/test")
public class TestController {


    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/userAdmin")
    @PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @PostMapping("/test")
    public String test() {

        return "Public Content.";
    }

}