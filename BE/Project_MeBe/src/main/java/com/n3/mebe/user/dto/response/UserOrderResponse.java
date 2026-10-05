package com.n3.mebe.user.dto.response;

import com.n3.mebe.order.entity.OrderStatus;
import com.n3.mebe.payment.entity.PaymentMethod;
import com.n3.mebe.payment.entity.PaymentStatus;


import com.n3.mebe.voucher.entity.Voucher;
import lombok.Data;

import java.util.Date;

@Data
public class UserOrderResponse {

    private int orderId;
    private Voucher voucher;
    private OrderStatus status;
    private String orderCode;
    private String shipAddress;
    private float deliveryFee;
    private float totalAmount;
    private float depositeAmount;
    private PaymentMethod orderType;
    private PaymentStatus paymentStatus;
    private String note;
    private Date createdAt;
    private Date updatedAt;
}
