package com.example.chuyendeweb.controller;

import com.example.chuyendeweb.entity.RefreshTokenEntity;
import com.example.chuyendeweb.exception.TokenRefreshException;
import com.example.chuyendeweb.model.request.*;
import com.example.chuyendeweb.model.response.JwtResponse;
import com.example.chuyendeweb.model.response.ResponseObject;
import com.example.chuyendeweb.model.response.TokenRefreshResponse;
import com.example.chuyendeweb.repository.security.CustomUserDetails;
import com.example.chuyendeweb.repository.security.RefreshTokenService;
import com.example.chuyendeweb.service.IUserService;
import com.example.chuyendeweb.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    IUserService iUserService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginReq LoginReq) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(LoginReq.getUsername(), LoginReq.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshTokenEntity refreshToken = refreshTokenService.finByIdUserEntity(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterReq RegisterReq) throws MessagingException, IOException {
        String result = iUserService.registerUser(RegisterReq);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(200, result, ""));
    }

    @PostMapping(value = "/verifyEmail")
    public ResponseEntity<?> VerifyEmail( @Valid @RequestBody VerifyCodeReq verifyCode) {
        boolean isCheckVerify = iUserService.verify(verifyCode.getVerifyCodeEmail());
        if (isCheckVerify)
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.value(), "Verification successful, you can now login", ""));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseObject(HttpStatus.NOT_FOUND.value(), "Verification failed,you need to check the verifyCode in the Email or verifyCode expire", ""));
    }

    @PostMapping("/refreshVerifyCode")
    public ResponseEntity<?> refreshVerifyCode(@Valid @RequestBody EmailReq refreshVerifyCodeReq) {
        boolean existEmail = iUserService.refeshVerifyCode(refreshVerifyCodeReq.getEmail());
        if (existEmail)
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.value(), "please check your email for verification instructions", ""));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseObject(HttpStatus.NOT_FOUND.value(), "This email does not exist in the database", ""));
    }
    @PostMapping(value = "/forgot-password")
    public ResponseEntity<?> forgotPassword( @Valid @RequestBody EmailReq forgotReq) {
        boolean isCheckforgot = iUserService.checkForgot(forgotReq.getEmail());
        if (isCheckforgot)
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.value(), "please check your email for verification instructions", ""));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseObject(HttpStatus.NOT_FOUND.value(), "Verification failed,the email you entered is wrong", ""));
    }
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest){
        boolean resetPassword= iUserService.ResetPassword(resetPasswordRequest);
        if(resetPassword)
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.value(),"reset Password successful",""));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseObject(HttpStatus.NOT_FOUND.value(),"reset Password fail",""));

    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshReq request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshTokenEntity::getUserEntity)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUserName());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) {
        boolean isCheckLogOut = iUserService.checklogout(logOutRequest);
        if(isCheckLogOut){
            return ResponseEntity.ok(new ResponseObject(HttpStatus.OK.value(), "Log out successful!", ""));
        }
        return ResponseEntity.ok(new ResponseObject(HttpStatus.BAD_REQUEST.value(), "Log out fail!", ""));
    }
    @PostMapping("/test")
    public String tesst(@RequestBody LoginReq loginReq){
        return "thien";

    }
}

