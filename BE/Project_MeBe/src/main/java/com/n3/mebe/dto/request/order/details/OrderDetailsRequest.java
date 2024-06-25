package com.n3.mebe.dto.request.order.details;


import lombok.Data;

@Data
public class OrderDetailsRequest {

    //Chi lay ve Id
    private int productId;
    private int quantity;
    private float price;
    private float salePrice;

}
