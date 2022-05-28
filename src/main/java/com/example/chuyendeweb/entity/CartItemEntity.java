package com.example.chuyendeweb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "cartItem")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemEntity extends BaseEntity {
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "productId",referencedColumnName = "id")
    private ProductEntity productEntities ;
    @Column
    private int quantity;
    @Column
    private int totalPrice;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cartId",referencedColumnName = "id")
    private CartEntity cartEntity;

}
