package com.example.chuyendeweb.service.imp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.chuyendeweb.common.ERole;
import com.example.chuyendeweb.entity.CartEntity;
import com.example.chuyendeweb.entity.OrderEntity;
import com.example.chuyendeweb.entity.RefreshTokenEntity;
import com.example.chuyendeweb.entity.RoleEntity;
import com.example.chuyendeweb.entity.UserEntity;
import com.example.chuyendeweb.exception.NotFoundException;
import com.example.chuyendeweb.model.request.RegisterEmail;
import com.example.chuyendeweb.model.request.RegisterReq;
import com.example.chuyendeweb.model.request.ResetPasswordRequest;
import com.example.chuyendeweb.model.response.AdminUserResponse;
import com.example.chuyendeweb.model.response.UserReponse;
import com.example.chuyendeweb.repository.CartRepository;
import com.example.chuyendeweb.repository.RefreshTokenRepository;
import com.example.chuyendeweb.repository.RoleRepository;
import com.example.chuyendeweb.repository.OrderRepository;
import com.example.chuyendeweb.repository.UserRepository;
import com.example.chuyendeweb.security.RefreshTokenService;
import com.example.chuyendeweb.service.IUserService;
import com.example.chuyendeweb.util.SendEmailUtils;

@Service
public class UserServiceImp implements IUserService {
	@Autowired
	OrderRepository orderRepo;
	@Autowired
	ModelMapper mapper;	
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    CartRepository cartRespository;
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
        // if (userRepository.existsByUserName(RegisterReq.getUsername())) {
        // throw new BadRequestException("Error: Username is already taken!");
        // }
        // if (userRepository.existsByEmail(RegisterReq.getEmail())) {
        // throw new BadRequestException("Error: Email is already in use!");
        // }
        // Create new user's account
        UserEntity user = this.userRepository.findByUserName(RegisterReq.getUserName()).get();
        user.setPasswords(encoder.encode(RegisterReq.getPassword()));
        user.setPhone(RegisterReq.getPhone());
        user.setGender(RegisterReq.getGender());

        Set<String> strRoles = null;
        Set<RoleEntity> roles = new HashSet<>();
        addRolesToUser(strRoles, roles);
        user.setRoles(roles);
        userRepository.save(user);
        // System.out.println(user.getId());
        refreshTokenService.createRefreshToken(user.getId());

        CartEntity cart = new CartEntity(new Date(), user);
        cartRespository.save(cart);

        return "registered successfully";
    }

    @Override
    public String registerEmail(RegisterEmail registerEmail) throws MessagingException, IOException {
        UserEntity user = new UserEntity(registerEmail.getName(), registerEmail.getEmail());
        user.setAddress("Ho Chi Minh");
        user.setStatuss("Active");
        user.setEnabled(false);
        setVerifyCodeEmail(user);
        // case time ơ day
        boolean check = this.userRepository.existsByUserName(registerEmail.getName());
        if (check) {
            user = this.userRepository.findByUserName(registerEmail.getName()).get();
            sendEmailUtils.sendEmailWithAttachment(user, user.getVerificationCode());
            userRepository.save(user);
            System.out.println(user);
            return "please check your email for verification instructions";
        }
        sendEmailUtils.sendEmailWithAttachment(user, user.getVerificationCode());
        userRepository.save(user);

        return "registered email successfully, please check your email for verification instructions";
    }

    @Override
    public UserEntity saveAndFlush(UserEntity user) {
        return this.userRepository.save(user);
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
                    sendEmailUtils.sendEmailWithAttachment(user, user.getVerificationCode());
                    userRepository.save(user);
                    return true;
                } else {
                    // System.out.println("code chưa hết hiệu lực");
                    throw new NotFoundException("code has not expired yet ");
                }
            }

        } catch (NullPointerException | MessagingException | IOException ex) {
            ex.printStackTrace();

        }
        return false;
    }

    @Override
    public boolean verify(int verificationCode) {
        UserEntity user = userRepository.findByVerificationCode(verificationCode);

        try {
            if (user == null || user.isEnabled()) {
                // throw new NotFoundException("verificationCode is incorrect or user is
                // disabled");
                return false;
            } else {
//                if (checkTimeVerifyCode(user)) {
                    user.setVerificationCode(0);
                    user.setEnabled(true);
                    userRepository.save(user);
                    return true;
//                } else {
//                    // throw new NotFoundException("verificationCode token was expired. Please make
//                    // a new refeshVerifyCode");
//                    return false;
//                }

            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();

        }
        return false;
    }

    @Override
    public boolean checkForgot(String email) {
        UserEntity user = userRepository.findByEmail(email);
        // System.out.println(user);
        try {
            if (user == null || !user.isEnabled()) {
                // System.out.println("asdfasdf");
                throw new NotFoundException("email is incorrect or user is disabled");
            } else {
                int code = (int) Math.floor(((Math.random() * 899999) + 100000));
                user.setVerifiForgot(code);
                sendEmailUtils.sendEmailWithAttachment(user, user.getVerifiForgot());
                userRepository.save(user);
                return true;
            }
        } catch (NullPointerException | IOException | MessagingException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean verifyForgot(int verificationCode) {
        UserEntity user = userRepository.findByVerifiForgot(verificationCode);
        // System.out.println(user);
        if (user == null) {
            return false;
        } else {
            user.setVerifiForgot(0);
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public boolean ResetPassword(ResetPasswordRequest resetPasswordRequest) {
        UserEntity user = userRepository.findByVerifiForgot(resetPasswordRequest.getVerifyCodeForgot());
        try {
            if (user == null || user.isEnabled()) {
                throw new NotFoundException("email is incorrect or user is disabled");
            } else {
                if (!resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmPassword())) {
                    throw new NotFoundException("password not the same");
                } else {
                    user.setPasswords(encoder.encode(resetPasswordRequest.getNewPassword()));
                    user.setVerifiForgot(0);
                    userRepository.save(user);
                    return true;
                }
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean checkTimeVerifyCode(UserEntity user) {
        return user.getDateCreated().getTime() + timeVerifyCode - new Date().getTime() >= 0;
    }

    public UserEntity setUserToGG(String username, String email) {
        UserEntity user = new UserEntity(username, email);
        user.setEnabled(true);
        user.setPasswords(encoder.encode("12345678"));
        Set<String> strRoles = null;
        Set<RoleEntity> roles = new HashSet<>();
        addRolesToUser(strRoles, roles);
        user.setRoles(roles);
        userRepository.saveAndFlush(user);
        // System.out.println("aaaaaaaaaaaaaaaaaaaa");
        refreshTokenService.createRefreshToken(user.getId());
        return user;
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
    public boolean checklogout(String userName) {
        UserEntity user = userRepository.findByUserName(userName).get();
        try {
            if (user == null) {
                throw new NotFoundException("id is incorrect");
            } else {
                user.setVerificationCode(0);
                user.setVerifiForgot(0);
                RefreshTokenEntity refreshToken = refreshTokenService.finByIdUserEntity(user.getId());
                refreshToken.setExpiryDate(null);
                refreshToken.setToken(null);
                refreshTokenRepository.saveAndFlush(refreshToken);
                userRepository.save(user);
                return true;
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public void setVerifyCodeEmail(UserEntity user) {
        int code = (int) Math.floor(((Math.random() * 899999) + 100000));
        user.setVerificationCode(code);
        user.setDateCreated(new Date());
    }

    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public UserEntity finByName(String username) {
        return this.userRepository.findByUserName(username).get();
    }

    @Override
    public boolean finByUserName(String username) {
        return userRepository.existsByUserName(username);
    }

    @Override
    public boolean finByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

	@Override
	public Map<String, Object> showListUser(int pageIndex, int pageSize) {
		   Pageable pageable = PageRequest.of(pageIndex, pageSize);
		   Map<String, Object> result = new HashMap<>();
		   Page<UserEntity> pageTuts ;
		    pageTuts = this.userRepository.findAll(pageable);
		    List<UserEntity> listUser=pageTuts.getContent();
		    List<AdminUserResponse> listResponseUser=new ArrayList<AdminUserResponse>();
		    for (UserEntity userEntity : listUser) {
		    	listResponseUser.add(mapper.map(userEntity, AdminUserResponse.class));
			}
		    for (AdminUserResponse adminUserResponse : listResponseUser) {
					Long id=adminUserResponse.getId();
					List<OrderEntity> listOrder=orderRepo.findByUserEntityId(id);
					long totalPrice = 0;
					int totalProduct = 0;
					for (OrderEntity order : listOrder) {
						
						 totalPrice+=order.getTotalPriceOrder();
						totalProduct+=1;
					}
					adminUserResponse.setTotalOrder(totalProduct);
					adminUserResponse.setTotalPrice(totalPrice);
					
			}
		    result.put("listUser", listResponseUser);
	        result.put("curerentPage", pageTuts.getNumber());
	        result.put("totalitems", pageTuts.getTotalElements());
	        result.put("totalPage", pageTuts.getTotalPages());
		return result;
	}

	@Override
	public void deleteIds(Long[] ids) {
			for (Long id : ids) {
				System.out.println(id);
				this.userRepository.deleteOneById(id);
			}
		
	}


}
