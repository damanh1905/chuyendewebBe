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
public class ProductEntity extends BaseEntity implements Serializable {
    @Column
    private int Price;
    @Column
    private int Price_Sale;
    @Column
    private int amount;
    @Column
    private boolean isNew;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "productDetailId", referencedColumnName = "id")
    private ProductDetailEntity productDetalEntity;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private CategoryEntity categoryEntity;
    @JsonIgnore
    @OneToMany(mappedBy = "productEntities")
    private Set<CartItemEntity> cartItemEntity;


}
