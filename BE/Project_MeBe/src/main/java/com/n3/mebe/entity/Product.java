package com.n3.mebe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private SubCategory subCategory;

    private String slug;

    @Column(name = "[name]")
    private String name;

    private String images;

    @Column(name = "[description]")
    private String description;

    private float price;

    @Column(name ="sale_price")
    private float salePrice;

    @Column(name ="[status]")
    private String status;

    @Column(name = "total_sold")
    private int totalSold;

    @Column(name = "product_view")
    private int productView;

    @Column(name = "created_at")
    private Date createAt;

    @Column(name = "updated_at")
    private Date updateAt;

    @OneToMany(mappedBy = "product")
    private Set<Inventory> inventories;

    @OneToMany(mappedBy = "product")
    private Set<Review> reviewsProducts;

    @OneToMany(mappedBy = "product")
    private Set<OrderDetail> orderDetails;
}
