package com.example.chuyendeweb.controller;

import com.example.chuyendeweb.entity.RefreshTokenEntity;
import com.example.chuyendeweb.entity.UserEntity;
import com.example.chuyendeweb.exception.NotFoundException;
import com.example.chuyendeweb.exception.TokenRefreshException;
import com.example.chuyendeweb.model.request.*;
import com.example.chuyendeweb.model.response.*;
import com.example.chuyendeweb.repository.UserRepository;
import com.example.chuyendeweb.security.CustomUserDetails;
import com.example.chuyendeweb.security.RefreshTokenService;
import com.example.chuyendeweb.service.IUserService;
import com.example.chuyendeweb.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController

@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    RefreshTokenService refreshTokenService;
    @Autowired
    IUserService iUserService;
    @Autowired
    UserRepository userpRepository;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginReq LoginReq, HttpServletResponse response) {

        CustomUserDetails userDetails = (CustomUserDetails) this.authuticate(LoginReq.getUsername(),
                LoginReq.getPassword());
        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());
        System.out.println(roles);
        RefreshTokenEntity refreshToken = refreshTokenService.finByIdUserEntity(userDetails.getId());
        // Cookie cookie = new Cookie("token",jwt);
        // cookie.setMaxAge(7 * 24 * 60 * 60); //
        // cookie.setHttpOnly(true);
        // cookie.setPath("/"); // global cookie accessible every where
        // Cookie refresh = new Cookie("refreshToken",refreshToken.getToken());
        // refresh.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
        // refresh.setSecure(true);
        // refresh.setMaxAge(0);
        // refresh.setHttpOnly(true);
        // refresh.setPath("/"); // global cookie accessible every where
        // response.addCookie(cookie);
        // response.addCookie(refresh);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new JwtResponse(HttpStatus.OK.value(), jwt, refreshToken.getToken(),
                        userDetails.getId(),
                        userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @PostMapping("/admin/login")
    public ResponseEntity<?> authenticateAdmin(@Valid @RequestBody LoginReq LoginReq, HttpServletResponse response) {

        CustomUserDetails userDetails = (CustomUserDetails) this.authuticate(LoginReq.getUsername(),
                LoginReq.getPassword());
        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());
        System.out.println(roles.size());
        JwtResponse jwtResponse = null;
        for (int i = 0; i < roles.size(); i++) {
            System.out.println(roles.get(i));
            if (roles.get(i).equals("ROLE_ADMIN")) {
                RefreshTokenEntity refreshToken = refreshTokenService.finByIdUserEntity(userDetails.getId());
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new JwtResponse(HttpStatus.OK.value(), jwt, refreshToken.getToken(),
                                userDetails.getId(),
                                userDetails.getUsername(), userDetails.getEmail(), roles));
            }
        }

        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterReq RegisterReq)
            throws MessagingException, IOException {
        String result = iUserService.registerUser(RegisterReq);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(200, result, ""));
    }

    @PostMapping(value = "/registerEmail")
    public ResponseEntity<?> registerEmail(@Valid @RequestBody RegisterEmail registerEmail)
            throws MessagingException, IOException {
        String result = this.iUserService.registerEmail(registerEmail);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.value(), result, ""));
    }

    @PostMapping(value = "/verifyEmail")
    public ResponseEntity<?> VerifyEmail(@Valid @RequestBody VerifyCodeReq verifyCode) {
        boolean isCheckVerify = iUserService.verify(verifyCode.getVerifyCodeEmail());
        if (isCheckVerify)
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.value(),
                            "Verification successful, you can now login", ""));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.NOT_FOUND.value(),
                        "Verification failed,you need to check the verifyCode in the Email or verifyCode expire",
                        ""));
    }

    @PostMapping("/refreshVerifyCode")
    public ResponseEntity<?> refreshVerifyCode(@Valid @RequestBody EmailReq refreshVerifyCodeReq) {
        boolean existEmail = iUserService.refeshVerifyCode(refreshVerifyCodeReq.getEmail());
        if (existEmail)
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.value(),
                            "please check your email for verification instructions", ""));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseObject(HttpStatus.NOT_FOUND.value(),
                        "This email does not exist in the database",
                        ""));
    }

    @PostMapping(value = "/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody EmailReq forgotReq) {
        boolean isCheckforgot = iUserService.checkForgot(forgotReq.getEmail());
        if (isCheckforgot)
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.value(),
                            "please check your email for verification instructions", ""));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseObject(HttpStatus.NOT_FOUND.value(),
                        "Verification failed,the email you entered is wrong", ""));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        boolean resetPassword = iUserService.ResetPassword(resetPasswordRequest);
        if (resetPassword)
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.value(), "reset Password successful",
                            ""));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseObject(HttpStatus.NOT_FOUND.value(), "reset Password fail", ""));

    }

    //        @PostMapping("/refreshtoken")
//        public ResponseEntity<?> refreshtoken(@CookieValue(value = "refreshToken",required = false)Cookie tokenCookie, HttpServletRequest request) {
//                if(tokenCookie ==null  ){
//                        System.out.println("sssss");
//                        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//                }
//
//                String headerAuth = request.getHeader("Authorization");
//                try {
//                        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
//                                String jwt = headerAuth.substring(7, headerAuth.length());
//
//                                if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
//                                        System.out.println(jwt);
//                                        return  ResponseEntity.ok("aa");
//                                }else {
//                                        System.out.println("vo day");
//                                        return refreshTokenService.findByToken(tokenCookie.getValue())
//                                                .map(refreshTokenService::verifyExpiration)
//                                                .map(RefreshTokenEntity::getUserEntity)
//                                                .map(user -> {
//                                                        String token = jwtUtils.generateTokenFromUsernameref(user.getUserName());
//                                                        return ResponseEntity
//                                                                .ok(new TokenRefreshResponse(HttpStatus.OK.value(), token,
//                                                                        ""));
//                                                })
//                                                .orElseThrow(() -> new TokenRefreshException("",
//                                                        "Refresh token is not in database!"));
//                                }
//                        }
//                }catch (Exception e) {
//
//                }
//
//                System.out.println("sssss");
//                return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshReq request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshTokenEntity::getUserEntity)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsernameref(user.getUserName());
                    return ResponseEntity
                            .ok(new TokenRefreshResponse(HttpStatus.OK.value(), token,
                                    requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestParam(value = "userName") String userName) {
        boolean isCheckLogOut = iUserService.checklogout(userName);
        if (isCheckLogOut) {
            return ResponseEntity.ok(new ResponseObject(HttpStatus.OK.value(), "Log out successful!", ""));
        }
        return ResponseEntity.ok(new ResponseObject(HttpStatus.BAD_REQUEST.value(), "Log out fail!", ""));
    }

    @GetMapping("/checkUserName")
    public ResponseEntity<?> checkUser(@RequestParam String username) {
        boolean check = this.iUserService.finByUserName(username);
        if (check)
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.NOT_FOUND.value(), "exit user!", ""));

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK.value(), "successful!", ""));
    }

    @GetMapping("/checkLoginGG")
    public ResponseEntity<?> checkLoginGG(@RequestParam String username, @RequestParam String email) {
        boolean check = this.iUserService.finByUserName(username);
        if (check) {
            UserEntity user = this.iUserService.finByName(username);
            CustomUserDetails userDetails = (CustomUserDetails) this.authuticate(user.getUserName(),
                    "12345678");
            String jwt = jwtUtils.generateJwtToken(userDetails);
            List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            RefreshTokenEntity refreshToken = refreshTokenService.finByIdUserEntity(userDetails.getId());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new JwtResponse(HttpStatus.OK.value(), jwt, refreshToken.getToken(),
                            userDetails.getId(),
                            userDetails.getUsername(), userDetails.getEmail(), roles));
        }
        UserEntity user = this.iUserService.setUserToGG(username, email);
        CustomUserDetails userDetails = (CustomUserDetails) this.authuticate(user.getUserName(), "12345678");
        String jwt = jwtUtils.generateJwtToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());
        RefreshTokenEntity refreshToken = refreshTokenService.finByIdUserEntity(userDetails.getId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new JwtResponse(HttpStatus.OK.value(), jwt, refreshToken.getToken(),
                        userDetails.getId(),
                        userDetails.getUsername(), userDetails.getEmail(), roles));

    }

    public UserDetails authuticate(String username, String password) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails;
    }

    @GetMapping("/getCurrentUser")
    public ResponseEntity<?> getCurrentUser(@RequestParam String username) {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            throw new NotFoundException("please login to purchase!");
        }
        Optional<UserEntity> user = userpRepository.findByUserName(username);
        UserReponse userReponse = new UserReponse(user.get().getUserName(), user.get().getEmail(),
                user.get().getPhone(), user.get().getGender());
        return ResponseEntity.status(HttpStatus.OK).body(userReponse);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserReponse userReponse) {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            throw new NotFoundException("please login to purchase!");
        }
        try {
            UserEntity userEntity = iUserService.finByName(userReponse.getUserName());
            userEntity.setUserName(userReponse.getUserName());
            userEntity.setEmail(userReponse.getEmail());
            userEntity.setPhone(userReponse.getPhone());
            userEntity.setGender(userReponse.getGender());
            userpRepository.save(userEntity);
            return ResponseEntity.status(HttpStatus.OK).body(new String("chinh sua thanh cong"));
        } catch (Exception e) {
            throw e;
        }

    }

    // page ForgotPassword
    @GetMapping("/refreshVerifyCodeForgotPassword")
    public ResponseEntity<?> refreshVerifyCodeForgotPassword(@RequestParam String email) {
        boolean existEmail = iUserService.checkForgot(email);
        if (existEmail)
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.value(),
                            "please check your email for verification instructions", ""));

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK.value(),
                        "This email does not exist in the database",
                        ""));
    }

    // page ForgotPassword
    @PostMapping(value = "/verifyEmailForgotPassword")
    public ResponseEntity<?> verifyEmailForgotPassword(@Valid @RequestBody VerifyCodeReq verifyCode) {
        // System.out.println(verifyCode);
        boolean isCheckVerify = iUserService.verifyForgot(verifyCode.getVerifyCodeEmail());
        if (isCheckVerify)
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.value(),
                            "Verification successful, you can now login", ""));

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.NOT_FOUND.value(),
                        "Verification failed,you need to check the verifyCode in the Email or verifyCode expire",
                        ""));
    }

    // page ForgotPassword
    @PostMapping(value = "/resetPassword")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordForEmail resetEmail) {
        System.out.println(resetEmail);
        UserEntity userEntity = userpRepository.findByEmail(resetEmail.getEmail());
        userEntity.setPasswords(encoder.encode(resetEmail.getPassword()));
        userpRepository.save(userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(new String("chinh sua thanh cong"));

    }
}
