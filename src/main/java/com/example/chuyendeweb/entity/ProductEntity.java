package com.example.chuyendeweb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")
public class ProductEntity extends BaseEntity {
    @Column
    private int Price;
    @Column
    private int Price_Sale;
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
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private CategoryEntity categoryEntity;
    @JsonIgnore
    @OneToMany(mappedBy = "productEntities")
    private Set<CartItemEntity> cartItemEntity;


}
