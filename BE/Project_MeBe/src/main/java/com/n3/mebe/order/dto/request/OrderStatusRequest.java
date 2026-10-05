package com.n3.mebe.order.dto.request;

import com.n3.mebe.order.entity.OrderStatus;


import lombok.Data;

@Data
public class OrderStatusRequest {
    private int orderId;
    private OrderStatus status;
}
