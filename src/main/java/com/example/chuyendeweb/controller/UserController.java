//package com.example.chuyendeweb.controller;
//
//
//import com.example.chuyendeweb.entity.User;
//import com.example.chuyendeweb.repository.UserRepository;
//import com.example.chuyendeweb.model.request.LoginReq;
//import com.example.chuyendeweb.model.request.RegisterReq;
//import com.example.chuyendeweb.model.response.ResponseObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//
//@RestController
////@Controller
////@CrossOrigin(origins = "http://localhost:3000")
//public class UserController {
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//    @Autowired
//    private UserRepository userRepository;
////    @Autowired
////    private JwtUtil jwtUtil;
////    @Autowired
////    private AuthenticationManager authenticationManager;
////    @GetMapping("/")
////    public String index(){
////        return "Welcome to user";
////    }
////
////    @PostMapping("/login")
////
////    public String generateToken(@RequestBody LoginDTO userDTO) throws Exception {
////        try {
////            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getName(),userDTO.getPassword()));
////        }catch (Exception ex){
////            throw  new Exception("inavalid userName/password");
////        }
////        return jwtUtil.generateToken(userDTO.getName());
////    }
//@GetMapping("/hello")
//public ResponseEntity<?> getProfile() {
//    return ResponseEntity.ok("Hello World ");
//}
//
//@PostMapping("/login")
//public ResponseEntity<?> login(@Valid @RequestBody LoginReq req) {
//try {
//
//
//    // Xác thực từ username và password.
//    Authentication authentication = authenticationManager.authenticate(
//            new UsernamePasswordAuthenticationToken(
//                    req.getName(),
//                    req.getPassword()
//            )
//    );
////if(authentication != null) {
//    // Nếu không xảy ra exception tức là thông tin hợp lệ
//    // Set thông tin authentication vào Security Context
//    SecurityContextHolder.getContext().setAuthentication(authentication);
//
//    // Gen token
//    String token = jwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal());
//
//    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "create token successfully", token));
//}
//catch (Exception e){
//    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseObject("fail", "wrong", ""));
//}
////}else{
////    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("fail", "wrong", ""));
//}
//    @PostMapping("/save")
//    public ResponseEntity<?> save(@RequestBody RegisterReq registerReq){
//        User userEntity = new User();
//        userEntity.setAddress(registerReq.getAddress());
//        userEntity.setUserName(registerReq.getUserName());
//        userEntity.setPasswords(registerReq.getPasswords());
//
//    userRepository.save(userEntity);
//    return  ResponseEntity.ok("ok");
//    }
//}
//
//

