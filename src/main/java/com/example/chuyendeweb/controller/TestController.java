package com.example.chuyendeweb.controller;

import com.example.chuyendeweb.exception.NotFoundException;
import com.example.chuyendeweb.security.CustomUserDetails;
import com.example.chuyendeweb.util.JwtUtils;
import com.example.chuyendeweb.util.SendEmailUtils;
import com.example.chuyendeweb.util.SiteUrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    private SendEmailUtils sendEmail;
    @Autowired
    private SiteUrlUtils siteUrlUtils;

    @GetMapping("/all")
    public String allAccess() {
        System.out.println( SecurityContextHolder.getContext().getAuthentication());
//        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")){
//                throw  new NotFoundException("login ahihihi");
//            }else{
//                CustomUserDetails userDetails =  (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//                System.out.println(userDetails.getId());
//                System.out.println(userDetails.getAuthorities());
//            }



        return "Public Content.";
    }

    @GetMapping("/userAdmin")
    @PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
    public String userAccess() {
        CustomUserDetails userDetails =  (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getId());
        System.out.println(userDetails.getAuthorities());
        return "User Content.";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        CustomUserDetails userDetails =  (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getId());
        System.out.println(userDetails.getAuthorities());
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        CustomUserDetails userDetails =  (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getId());
        System.out.println(userDetails.getAuthorities());
        return "Admin Board.";
    }

    @PostMapping("/test")
    public String test() {

        return "Public Content.";
    }
}