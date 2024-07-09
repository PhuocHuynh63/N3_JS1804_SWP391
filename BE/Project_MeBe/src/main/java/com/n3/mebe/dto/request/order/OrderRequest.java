package com.n3.mebe.dto.request.order;


import com.n3.mebe.dto.request.order.details.OrderDetailsRequest;
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
    private String status;
    private float totalAmount;
    private String orderType;
    private String paymentStatus;
    private String note;
    private List<OrderDetailsRequest> item;
    private String transactionReference;


}
