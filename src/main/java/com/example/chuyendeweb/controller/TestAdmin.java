package com.example.chuyendeweb.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/admin")
@RestController
public class TestAdmin {
    @GetMapping("/all")
    public String test() {
        return "admin auth admin";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('MODERATOR')")
    public String adminAccess() {
        return "mod Board.";
    }
}
