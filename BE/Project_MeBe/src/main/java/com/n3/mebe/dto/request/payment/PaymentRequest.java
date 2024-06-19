package com.n3.mebe.dto.request.payment;

import lombok.Data;

@Data
public class PaymentRequest {

    private int orderId;
    private String bankCode;
}
