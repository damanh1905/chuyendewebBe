package com.example.chuyendeweb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "genders")
public class GendersEntity  extends  BaseEntity{
    @Column
    String code;
    @Column
    String name;
    @JsonIgnore
    @ManyToMany(mappedBy = "genders")
    private List<ProductEntity> products;
}
