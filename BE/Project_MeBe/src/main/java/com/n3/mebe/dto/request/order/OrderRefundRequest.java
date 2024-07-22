package com.n3.mebe.dto.request.order;

import com.n3.mebe.dto.request.order.details.OrderDetailsRequest;

import lombok.Data;


import java.util.List;


@Data
public class OrderRefundRequest {

    String email;
    private String orderCode;
    private String note;
    private float totalAmount;
    private List<OrderDetailsRequest> orderDetails;

}
