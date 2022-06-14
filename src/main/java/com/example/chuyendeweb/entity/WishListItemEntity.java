package com.example.chuyendeweb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wishListItem")
public class WishListItemEntity extends  BaseEntity{

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "productId",referencedColumnName = "id")
    private ProductEntity productEntities ;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "wishListId",referencedColumnName = "id")
    private WishListEntity wishList ;
}
