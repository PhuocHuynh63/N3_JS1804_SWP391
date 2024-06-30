package com.n3.mebe.service.iml.paymentOrder;

import com.n3.mebe.entity.Order;
import com.n3.mebe.entity.Payment;
import com.n3.mebe.repository.IPaymentRepository;
import com.n3.mebe.service.IPaymentService;
import com.n3.mebe.util.DataUtils;
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

        Date now = new Date();
        payment.setCreateAt(now);
        payment.setUpdateAt(now);
        if(order.getOrderType().equals("COD")){
           payment.setTransactionReference(DataUtils.generateTempPwd(8));
        }else if(order.getOrderType().equals("Online")){
            payment.setTransactionReference(transactionReference);
        }
        paymentRepository.save(payment);
    }
}
