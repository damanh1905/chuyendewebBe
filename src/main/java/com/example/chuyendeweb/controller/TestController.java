package com.example.chuyendeweb.controller;

import com.example.chuyendeweb.security.CustomUserDetails;
import com.example.chuyendeweb.util.SendEmailUtils;
import com.example.chuyendeweb.util.SiteUrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    private SendEmailUtils sendEmail;
    @Autowired
    private SiteUrlUtils siteUrlUtils;

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