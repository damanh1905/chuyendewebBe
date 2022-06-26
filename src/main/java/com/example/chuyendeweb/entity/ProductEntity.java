package com.example.chuyendeweb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")
public class ProductEntity extends BaseEntity {
    @Column
    private int price;
    @Column
    private int price_Sale;
    @Column
    private int amount;
    @Column
    private boolean isNew;
    @Column
    private String sourceOrigin;
    @Column
    private String name;
    @Column
    private String descriptions;
    @Column
    private Date importDate;
    @Column
    private Date expiryDate;
    @JsonIgnore
    @ManyToMany( fetch = FetchType.EAGER)
    @JoinTable(name = "productGender", joinColumns = { @JoinColumn(name = "productId") }, inverseJoinColumns = {
            @JoinColumn(name = "genderId") })

    private Set<GendersEntity> genders;
    @JsonIgnore
    @ManyToMany( fetch = FetchType.EAGER)
    @JoinTable(name = "productAge", joinColumns = { @JoinColumn(name = "productId") }, inverseJoinColumns = {
            @JoinColumn(name = "ageId") })

    private Set<AgesEntity> ages;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private CategoryEntity categoryEntity;
    @JsonIgnore
    @OneToMany(mappedBy = "productEntities")
    private List<CartItemEntity> cartItemEntity;
    @JsonIgnore
    @OneToMany(mappedBy = "productEntity",cascade = CascadeType.ALL)
    private List<ImageEntity> ImageEntity;
    @JsonIgnore
    @OneToMany(mappedBy = "productEntities")
    private List<WishListItemEntity> wishListItems;

}
