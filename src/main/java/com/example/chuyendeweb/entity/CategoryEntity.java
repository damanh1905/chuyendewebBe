package com.example.chuyendeweb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Table(name = "category")
@Entity
public class CategoryEntity extends BaseEntity {
    @JsonIgnore
    @Column
    private String NameCategory;
    @OneToMany(mappedBy = "categoryEntity")
    private List<ProductEntity> productEntitys ;

}
