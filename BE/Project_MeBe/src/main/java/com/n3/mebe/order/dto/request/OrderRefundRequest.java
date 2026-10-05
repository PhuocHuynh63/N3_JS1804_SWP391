package com.n3.mebe.order.dto.request;

import java.math.BigDecimal;
import com.n3.mebe.order.dto.request.OrderDetailsRequest;

import lombok.Data;


import java.util.List;


@Data
public class OrderRefundRequest {

    String email;
    private String orderCode;
    private String note;
    private BigDecimal totalAmount;
    private List<OrderDetailsRequest> orderDetails;

}
