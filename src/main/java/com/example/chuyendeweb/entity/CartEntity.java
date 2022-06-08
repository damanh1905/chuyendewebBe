package com.example.chuyendeweb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;
@ToString
@Entity
@Getter
@Setter
@Table(name = "cart")
public class CartEntity extends BaseEntity {
    @Temporal(TemporalType.DATE)
    @Column
    private Date lastModified;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userID", referencedColumnName = "id")
    private UserEntity userEntity;
    @JsonIgnore
     @OneToMany(mappedBy = "cartEntity")
    private List<CartItemEntity> cartItemEntity;

    public CartEntity(Date lastModified, UserEntity userEntity) {
        super();
        this.lastModified = lastModified;
        this.userEntity = userEntity;
    }

    public CartEntity() {

    }

}
