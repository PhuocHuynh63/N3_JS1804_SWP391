package com.n3.mebe.service;

import com.n3.mebe.entity.Order;

public interface IPaymentService {

    void savePayment(Order order, String transactionReference);
}
