package com.n3.mebe.dto.request.order;


import com.n3.mebe.entity.OrderDetail;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    // neu nhu day la GUESS thi se chuyen vao
    private OrderUserCreateRequest guess;

    //lay userId tu request
    private int userId;
    //lay voucherId tu request
    private int voucherId;

    private String status;
    private float deliveryFee;
    private float totalAmount;
    private float depositeAmount;
    private String orderType;
    private String paymentStatus;
    private String note;


}
