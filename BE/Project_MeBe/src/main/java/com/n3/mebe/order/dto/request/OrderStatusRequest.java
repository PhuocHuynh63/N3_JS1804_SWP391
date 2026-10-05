package com.n3.mebe.order.dto.request;


import lombok.Data;

@Data
public class OrderStatusRequest {
    private int orderId;
    private String status;
}
