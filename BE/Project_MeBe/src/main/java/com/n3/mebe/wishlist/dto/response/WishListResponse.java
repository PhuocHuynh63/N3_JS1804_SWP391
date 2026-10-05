package com.n3.mebe.wishlist.dto.response;

import com.n3.mebe.wishlist.entity.WishListStatus;

import com.n3.mebe.catalog.dto.response.ProductResponse;
import com.n3.mebe.catalog.entity.Product;
import lombok.Data;

import java.util.Date;

@Data
public class WishListResponse {

    private WishListUserResponse user;
    private ProductResponse product;

    private WishListStatus status;
    private int quantity;
    private float totalAmount;
    private Date estimatedDate;
    private Date createdAt;
    private Date updatedAt;
}
