package com.n3.mebe.payment.dto.request;

import com.n3.mebe.order.dto.request.OrderRequest;
import lombok.Data;

@Data
public class PaymentRequest {

    private String bankCode;
    private String type;
    private long amount;
}
