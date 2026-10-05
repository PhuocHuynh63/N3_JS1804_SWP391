package com.n3.mebe.wishlist.dto.request;

import java.math.BigDecimal;
import lombok.Data;

import java.util.Date;
@Data
public class WishListRequest {

    private int userId;
    private int productId;
    private int quantity;
    private BigDecimal totalAmount;

}
