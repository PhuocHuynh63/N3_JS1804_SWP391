package com.n3.mebe.dto.response.wishList;

import com.n3.mebe.dto.response.product.ProductResponse;
import com.n3.mebe.entity.Product;
import lombok.Data;

import java.util.Date;

@Data
public class WishListResponse {

    private WishListUserResponse user;
    private ProductResponse product;

    private String status;
    private int quantity;
    private float totalAmount;
    private Date estimatedDate;
    private Date createdAt;
    private Date updatedAt;
}
