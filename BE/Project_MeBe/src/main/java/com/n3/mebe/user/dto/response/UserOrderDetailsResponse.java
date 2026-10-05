package com.n3.mebe.user.dto.response;

import java.math.BigDecimal;

import com.n3.mebe.user.dto.response.UserProductResponse;
import lombok.Data;

@Data
public class UserOrderDetailsResponse {

    private int odId;
    private UserProductResponse product;
    private int quantity;
    private BigDecimal price;
    private BigDecimal salePrice;
}
