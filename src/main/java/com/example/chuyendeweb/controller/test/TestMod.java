package com.example.chuyendeweb.controller.test;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/mod")
public class TestMod {

    @GetMapping("/all")
    public String test() {
        return "mod auth ";
    }

    @GetMapping("/admin")
//    @PreAuthorize("hasRole('AMIN')")
    public String adminAccess() {
        return "admin Board.";
    }
}
