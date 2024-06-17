package com.n3.mebe.dto.response.order;


import com.n3.mebe.entity.User;
import com.n3.mebe.entity.Voucher;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class OrderResponse {

    private int orderId;
    private User user;
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
