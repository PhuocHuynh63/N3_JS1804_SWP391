package com.n3.mebe.dto.request.wishList;

import lombok.Data;

import java.util.Date;
@Data
public class WishListRequest {

    private int userId;
    private int productId;
    private String status;
    private int quantity;
    private float totalAmount;
    private Date estimatedDate;
    private Date createdAt;
    private Date updatedAt;
}
