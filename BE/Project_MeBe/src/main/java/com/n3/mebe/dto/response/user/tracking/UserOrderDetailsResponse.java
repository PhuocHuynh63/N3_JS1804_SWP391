package com.n3.mebe.dto.response.user.tracking;


import com.n3.mebe.dto.response.user.UserProductResponse;
import lombok.Data;

@Data
public class UserOrderDetailsResponse {

    private int odId;
    private UserProductResponse product;
    private int quantity;
    private float price;
    private float salePrice;
}
