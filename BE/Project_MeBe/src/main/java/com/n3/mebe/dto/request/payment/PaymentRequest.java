package com.n3.mebe.dto.request.payment;

import com.n3.mebe.dto.request.order.OrderRequest;
import lombok.Data;

@Data
public class PaymentRequest {
    private OrderRequest orderRequest;
    private String bankCode;
    private String type;
    private long amount;
}
