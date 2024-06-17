package com.n3.mebe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subcategory")
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subcategory_id")
    private int subCateId;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category category;

    private String name;

    private String slug;

    @OneToMany(mappedBy = "subCategory")
    private Set<Product> product;
}
