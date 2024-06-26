package com.n3.mebe.dto.request.payment;

import lombok.Data;

@Data
public class PaymentRequest {


    private String bankCode;
    private String type;
    private long amount;
}
