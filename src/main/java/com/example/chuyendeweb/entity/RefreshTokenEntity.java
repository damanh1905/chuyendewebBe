package com.example.chuyendeweb.entity;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "refreshtoken")
public class RefreshTokenEntity extends BaseEntity {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userID", referencedColumnName = "id")
    private UserEntity userEntity;
    @Column()
    private String token;
    @Column
    private Instant expiryDate;
}
