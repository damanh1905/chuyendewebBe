package com.example.chuyendeweb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Table(name = "ages")
public class AgesEntity extends BaseEntity {
    @Column
    String code;
    @Column
    String name;
    @JsonIgnore
    @ManyToMany(mappedBy = "ages")
    private List<ProductEntity> products;
}
