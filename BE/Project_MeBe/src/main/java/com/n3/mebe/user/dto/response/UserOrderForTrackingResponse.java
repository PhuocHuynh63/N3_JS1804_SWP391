package com.n3.mebe.user.dto.response;

import com.n3.mebe.order.entity.OrderStatus;


import com.n3.mebe.voucher.entity.Voucher;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserOrderForTrackingResponse {
    private int orderId;
    private OrderStatus status;
    private Date createdAt;
    List<UserOrderDetailsResponse> items;

}
