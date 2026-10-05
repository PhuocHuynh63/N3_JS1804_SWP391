package com.n3.mebe.payment.service.impl;

import com.n3.mebe.order.entity.Order;
import com.n3.mebe.payment.entity.Payment;
import com.n3.mebe.payment.repository.IPaymentRepository;
import com.n3.mebe.payment.service.IPaymentService;
import com.n3.mebe.shared.util.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class PaymentService implements IPaymentService {

    @Autowired
    private IPaymentRepository paymentRepository;

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
        if(order.getOrderType().equals("COD")){
            payment.setPaymentStatus("Chưa thanh toán");
            payment.setTransactionReference(DataUtils.generateTempPwd(8));
        }else if(order.getOrderType().equals("Online")){
            payment.setPaymentStatus("Đã thanh toán");
            payment.setTransactionReference(transactionReference);
        }
        paymentRepository.save(payment);
    }

    @Override
    public void setStatusPayment(Order order) {

    }
}
