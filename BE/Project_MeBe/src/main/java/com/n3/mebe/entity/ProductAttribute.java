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
@Table(name = "product_attribute")
public class ProductAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_attribute_id")
    private int paId;

    private String type;

    private String value;

    @OneToMany(mappedBy = "sizeAttributeId")
    private Set<Inventory> productSizeSkus;

    @OneToMany(mappedBy = "colorAttributeId")
    private Set<Inventory> productColorSkus;
}
