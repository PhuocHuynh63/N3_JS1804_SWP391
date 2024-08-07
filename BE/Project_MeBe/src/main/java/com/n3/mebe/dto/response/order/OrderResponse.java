package com.n3.mebe.dto.response.order;


import com.n3.mebe.entity.User;
import com.n3.mebe.entity.Voucher;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class OrderResponse {

    private int orderId;
    private OrderUserResponse user;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    private Voucher voucher;
    private String orderCode;
    private String shipAddress;
    private String status;
    private float totalAmount;
    private String orderType;
    private String paymentStatus;
    private String note;
    private Date createdAt;
    private Date updatedAt;
    
}
