package com.n3.mebe.dto.request.order;


import lombok.Data;

@Data
public class OrderStatusRequest {
    private int orderId;
    private String status;
}
