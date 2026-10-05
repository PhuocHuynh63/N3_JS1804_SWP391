package com.n3.mebe.payment.service;

import com.n3.mebe.order.entity.Order;

public interface IPaymentService {

    void savePayment(Order order, String transactionReference);

    void setStatusPayment(Order order);
}
