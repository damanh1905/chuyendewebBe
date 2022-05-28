package com.example.chuyendeweb.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "coupon")
public class CouponEntity extends BaseEntity {
    @Column
    private String name;

    @Column
    private String code;

    @Column
    private int discount;
}
