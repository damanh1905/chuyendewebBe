package com.example.chuyendeweb.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Entity
@Table(name = "ProductDetail")
public class ProductDetalEntity extends BaseEntity {
    @Column
    private String sourceOrigin;
    @Column
    private String nameProduct;
    @Column

    private String descriptions;
    @OneToOne(mappedBy = "productDetalEntity")
    private ProductEntity productEntity;
}
