package com.n3.mebe.order.dto.request;

import java.math.BigDecimal;
import com.n3.mebe.order.entity.OrderStatus;
import com.n3.mebe.payment.entity.PaymentMethod;
import com.n3.mebe.payment.entity.PaymentStatus;


import com.n3.mebe.order.dto.request.OrderDetailsRequest;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    // neu nhu day la GUESS thi se chuyen vao
    private OrderUserCreateRequest guest;
    //lay userId tu request
    private int userId;
    //lay voucherId tu request
    private int voucherId;
    private String shipAddress;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private PaymentMethod orderType;
    private PaymentStatus paymentStatus;
    private String note;
    private List<OrderDetailsRequest> item;
    private String transactionReference;


}
