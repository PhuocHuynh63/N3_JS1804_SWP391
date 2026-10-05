package com.n3.mebe.wishlist.entity;

import java.math.BigDecimal;
import com.n3.mebe.catalog.entity.Product;
import com.n3.mebe.user.entity.User;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Entity
@Getter
@Setter
@Table(name = "wishlist")
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_id")
    private int wishlistId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "[status]")
    @Convert(converter = WishListStatus.JpaConverter.class)
    private WishListStatus status;

    private int quantity;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "estimated_date")
    private Date estimatedDate;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

}
