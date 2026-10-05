package com.n3.mebe.catalog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "[name]")
    private String name;

    @Column(name = "[slug]")
    private String slug;

    @OneToMany(mappedBy = "category")
    private Set<SubCategory> subCategories;

}
