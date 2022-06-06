package com.example.chuyendeweb.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "userName"),
                @UniqueConstraint(columnNames = "email")
        })
public class UserEntity extends BaseEntity {
    @NotBlank
    @Size(max = 20)
//    @Length(min = 5,max = 20, message = "*Your password must have at least 5 characters")
    @Column
    private String userName;
    @Column
    @NotBlank
    @Size(max = 50)
    private String email;
    @Column
    @NotBlank
    @Size(max = 120)
    private String passwords;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "userRoles",
            joinColumns = @JoinColumn(name = "userID"), inverseJoinColumns = @JoinColumn(name = "roleID"))
    private Set<RoleEntity> roles = new HashSet<>();
    @Column
    private String address;
    @Column
    private String phone;
    @Column
    private String gender;
    @Column
    private boolean enabled;
    @Column
    private String verificationCode;
    @Column
    private String verifiForgot;
    @Column
    private String statuss;
    @OneToOne(mappedBy = "userEntity")
    private CartEntity cartEntity;
    @OneToOne(mappedBy = "userEntity")
    private RefreshTokenEntity refreshToken;

    public UserEntity(String username, String email, String password) {
        this.userName = username;
        this.email = email;
        this.passwords = password;
    }

}
