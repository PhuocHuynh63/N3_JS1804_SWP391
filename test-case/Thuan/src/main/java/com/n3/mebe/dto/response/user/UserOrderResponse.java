package com.n3.mebe.dto.response.user;


import com.n3.mebe.entity.Voucher;
import lombok.Data;

import java.util.Date;

@Data
public class UserOrderResponse {

    private int orderId;
    private Voucher voucher;
    private String status;
    private float deliveryFee;
    private float totalAmount;
    private float depositeAmount;
    private String orderType;
    private String paymentStatus;
    private String note;
    private Date createdAt;
    private Date updatedAt;
}
