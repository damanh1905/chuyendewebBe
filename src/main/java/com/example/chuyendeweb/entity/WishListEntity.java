package com.example.chuyendeweb.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wishList")
public class WishListEntity extends BaseEntity{
    @JsonIgnore
    @OneToMany(mappedBy = "wishList")
    private List<WishListItemEntity> wishListItems;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userID", referencedColumnName = "id")
    private UserEntity userEntity;

    public WishListEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
