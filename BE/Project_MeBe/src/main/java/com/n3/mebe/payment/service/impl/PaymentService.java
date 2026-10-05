package com.n3.mebe.payment.service.impl;

import lombok.RequiredArgsConstructor;

import com.n3.mebe.order.entity.Order;
import com.n3.mebe.payment.entity.Payment;
import com.n3.mebe.payment.entity.PaymentMethod;
import com.n3.mebe.payment.entity.PaymentStatus;
import com.n3.mebe.payment.repository.IPaymentRepository;
import com.n3.mebe.payment.service.IPaymentService;
import com.n3.mebe.shared.util.DataUtils;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {

    private final IPaymentRepository paymentRepository;

    @Override
    public void savePayment(Order order, String transactionReference) {

        Payment payment = new Payment();

        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentType(order.getOrderType());
        payment.setPaymentStatus(order.getPaymentStatus());
        Date now = new Date();
        payment.setCreateAt(now);
        payment.setUpdateAt(now);
        if (order.getOrderType() == PaymentMethod.COD) {
            payment.setPaymentStatus(PaymentStatus.UNPAID);
            payment.setTransactionReference(DataUtils.generateTempPwd(8));
        } else if (order.getOrderType() != null && order.getOrderType().isOnline()) {
            payment.setPaymentStatus(PaymentStatus.PAID);
            payment.setTransactionReference(transactionReference);
        }
        paymentRepository.save(payment);
    }

    @Override
    public void setStatusPayment(Order order) {

    }
}
