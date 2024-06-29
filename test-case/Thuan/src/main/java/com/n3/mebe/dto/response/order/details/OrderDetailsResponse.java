package com.n3.mebe.dto.response.order.details;


import com.n3.mebe.dto.response.order.OrderResponse;
import com.n3.mebe.entity.Product;
import lombok.Data;

@Data
public class OrderDetailsResponse {

    private int odId;

    private OrderResponse order;
    private Product product;
    private int quantity;
    private float price;
    private float salePrice;
}
