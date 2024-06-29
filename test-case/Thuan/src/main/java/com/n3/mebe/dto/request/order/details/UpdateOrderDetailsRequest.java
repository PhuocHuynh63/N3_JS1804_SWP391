package com.n3.mebe.dto.request.order.details;

import lombok.Data;

@Data
public class UpdateOrderDetailsRequest {

    private int orderDetailId;

    private int orderId;
    private int inventoryId;
    private int quantity;
    private float price;
    private float salePrice;
}
