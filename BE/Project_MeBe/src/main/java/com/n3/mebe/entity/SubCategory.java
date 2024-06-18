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

<<<<<<< HEAD
    private String image;
=======
    private String slug;
>>>>>>> a69cb649d3ba6b1025674b237b9b5ba5c3946db0

    @OneToMany(mappedBy = "subCategory")
    private Set<Product> product;
}
