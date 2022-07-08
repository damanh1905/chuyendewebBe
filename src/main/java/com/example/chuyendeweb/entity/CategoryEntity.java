package com.example.chuyendeweb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CategoryEntity extends BaseEntity {
    @JsonIgnore
    @Column
    private String NameCategory;
    @OneToMany(mappedBy = "categoryEntity")
    private List<ProductEntity> productEntitys ;

}
