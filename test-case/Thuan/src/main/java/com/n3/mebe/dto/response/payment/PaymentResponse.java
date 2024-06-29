package com.n3.mebe.dto.response.payment;

import lombok.Data;

@Data
public class PaymentResponse {

    private String status;
    private String message;
    private String URL;
    private String paymentID;

}
