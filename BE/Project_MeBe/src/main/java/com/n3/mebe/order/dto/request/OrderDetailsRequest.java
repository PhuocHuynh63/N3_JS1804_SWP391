package com.n3.mebe.order.dto.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderDetailsRequest {

    //Chi lay ve Id
    private int productId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal salePrice;

}
