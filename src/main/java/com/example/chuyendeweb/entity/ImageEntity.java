package com.example.chuyendeweb.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "image")
public class ImageEntity extends BaseEntity {
    @Column
    private String url;
    @ManyToOne
    @JoinColumn(name = "productId",referencedColumnName = "id")
    ProductEntity productEntity;
}
