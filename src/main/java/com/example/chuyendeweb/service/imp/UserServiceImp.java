package com.example.chuyendeweb.service.imp;

import com.example.chuyendeweb.common.ERole;
import com.example.chuyendeweb.entity.CartEntity;
import com.example.chuyendeweb.entity.RefreshTokenEntity;
import com.example.chuyendeweb.entity.RoleEntity;
import com.example.chuyendeweb.entity.UserEntity;
import com.example.chuyendeweb.exception.BadRequestException;
import com.example.chuyendeweb.exception.NotFoundException;
import com.example.chuyendeweb.model.request.LogOutRequest;
import com.example.chuyendeweb.model.request.RegisterReq;
import com.example.chuyendeweb.model.request.ResetPasswordRequest;
import com.example.chuyendeweb.repository.CartRespository;
import com.example.chuyendeweb.repository.RefreshTokenRepository;
import com.example.chuyendeweb.repository.RoleRepository;
import com.example.chuyendeweb.repository.UserRepository;
import com.example.chuyendeweb.security.RefreshTokenService;
import com.example.chuyendeweb.service.IUserService;
import com.example.chuyendeweb.util.SendEmailUtils;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImp implements IUserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    CartRespository cartRespository;
    @Autowired
    private SendEmailUtils sendEmailUtils;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Value("${jtw.app.timeVerifycode}")
    private int timeVerifyCode;


    @Override
    public String registerUser(RegisterReq RegisterReq) throws IOException, MessagingException {
        if (userRepository.existsByUserName(RegisterReq.getUsername())) {
            throw new BadRequestException("Error: Username is already taken!");
        }
        if (userRepository.existsByEmail(RegisterReq.getEmail())) {
            throw new BadRequestException("Error: Email is already in use!");
        }
        // Create new user's account
        UserEntity user = new UserEntity(RegisterReq.getUsername(),
                RegisterReq.getEmail(),
                encoder.encode(RegisterReq.getPassword()));

        Set<String> strRoles = RegisterReq.getRole();
        Set<RoleEntity> roles = new HashSet<>();
        addRolesToUser(strRoles, roles);
        user.setRoles(roles);
        user.setEnabled(true);
        setVerifyCodeEmail(user);
        user.setPhone("123");
        userRepository.save(user);
        refreshTokenService.createRefreshToken(user.getId());
        System.out.println(user);
     CartEntity cart = new CartEntity(new Date(),user);
       cartRespository.save(cart);
//        sendEmailUtils.sendEmailWithAttachment(user,user.getVerificationCode());
        return "registered successfully, please check your email for verification instructions";
    }
    @Override
    public boolean refeshVerifyCode(String email) {
        UserEntity user = userRepository.findByEmail(email);
        try {
            if (user == null || user.isEnabled()) {
                throw new NotFoundException("email is incorrect or user is disabled");
            } else {
                if (!checkTimeVerifyCode(user)) {
                    setVerifyCodeEmail(user);
                    sendEmailUtils.sendEmailWithAttachment(user,user.getVerificationCode());
                    userRepository.save(user);
                    return true;
                } else {
                    System.out.println("code chưa hết hiệu lực");
                    throw new NotFoundException("code has not expired yet ");
                }
            }


        } catch (NullPointerException | MessagingException | IOException ex) {
            ex.printStackTrace();

        }
        return false;
    }
    @Override
    public boolean verify(String verificationCode) {
        UserEntity user = userRepository.findByVerificationCode(verificationCode);

        try {
            if (user == null || user.isEnabled()) {
                throw new NotFoundException("verificationCode is incorrect or user is disabled");
            } else {
                if (checkTimeVerifyCode(user)) {
                    user.setVerificationCode(null);
                    user.setEnabled(true);
                    userRepository.save(user);
                    return true;
                } else {
                  throw  new NotFoundException("verificationCode token was expired. Please make a new refeshVerifyCode");
                }

            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();

        }
        return false;
    }
    @Override
    public boolean checkForgot(String email) {
        UserEntity user = userRepository.findByEmail(email);
        try {
            if (user == null || user.isEnabled()) {
                throw new NotFoundException("email is incorrect or user is disabled");
            } else {
                String randomCode = RandomString.make(64);
                user.setVerifiForgot(randomCode);
                sendEmailUtils.sendEmailWithAttachment(user,user.getVerifiForgot());
                userRepository.save(user);
                return  true;
            }
        } catch (NullPointerException | IOException | MessagingException ex) {

            ex.printStackTrace();

        }
        return false;
    }

    @Override
    public boolean ResetPassword(ResetPasswordRequest resetPasswordRequest) {
        UserEntity user = userRepository.findByVerifiForgot(resetPasswordRequest.getVerifyCodeForgot());
        try {
            if (user == null || user.isEnabled()) {
                throw new NotFoundException("email is incorrect or user is disabled");
            }else{
                if(!resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmPassword())){
                    throw  new NotFoundException("password not the same");
                }else{
                    user.setPasswords(encoder.encode(resetPasswordRequest.getNewPassword()));
                    user.setVerifiForgot(null);
                    userRepository.save(user);
                    return true;
                }
            }
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }
        return  false;
    }
    public boolean checkTimeVerifyCode(UserEntity user) {
        return user.getDateCreated().getTime() + timeVerifyCode - new Date().getTime() >= 0;
    }

    public void addRolesToUser(Set<String> strRoles, Set<RoleEntity> roles) {
        if (strRoles == null) {
            RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        RoleEntity adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        RoleEntity modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
    }
    @Override
    public boolean checklogout(LogOutRequest logOutRequest) {
        UserEntity user = userRepository.findByUserID(logOutRequest.getUserId());
       try {
           if(user == null){
               throw  new NotFoundException("id is incorrect");
           }else{
               user.setVerificationCode(null);
               user.setVerifiForgot(null);
               RefreshTokenEntity refreshToken = refreshTokenService.finByIdUserEntity(user.getId());
               refreshToken.setExpiryDate(null);
               refreshToken.setToken(null);
               refreshTokenRepository.saveAndFlush(refreshToken);
               userRepository.save(user);
               return  true;
           }
       }catch (NullPointerException ex){
           ex.printStackTrace();
       }

        return false;
    }
public void setVerifyCodeEmail(UserEntity user){
    String randomCode = RandomString.make(64);
    user.setVerificationCode(randomCode);
    user.setDateCreated(new Date());
}
    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id).get();
    }
}


