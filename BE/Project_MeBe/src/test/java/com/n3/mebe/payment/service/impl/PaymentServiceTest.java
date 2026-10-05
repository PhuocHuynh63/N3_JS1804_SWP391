package com.n3.mebe.payment.service.impl;

import com.n3.mebe.order.entity.Order;
import com.n3.mebe.payment.entity.Payment;
import com.n3.mebe.payment.entity.PaymentMethod;
import com.n3.mebe.payment.entity.PaymentStatus;
import com.n3.mebe.payment.repository.IPaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private IPaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void savePayment_cod_isUnpaidWithGeneratedReference() {
        Order order = order(PaymentMethod.COD);

        paymentService.savePayment(order, null);

        Payment saved = capturePayment();
        assertThat(saved.getPaymentStatus()).isEqualTo(PaymentStatus.UNPAID);
        assertThat(saved.getTransactionReference()).hasSize(8);
        assertThat(saved.getAmount()).isEqualByComparingTo("150000");
    }

    @Test
    void savePayment_vnpay_isPaidAndKeepsTransactionReference() {
        Order order = order(PaymentMethod.VNPAY);

        paymentService.savePayment(order, "TXN-123");

        Payment saved = capturePayment();
        assertThat(saved.getPaymentStatus()).isEqualTo(PaymentStatus.PAID);
        assertThat(saved.getTransactionReference()).isEqualTo("TXN-123");
    }

    private Order order(PaymentMethod method) {
        Order order = new Order();
        order.setOrderType(method);
        order.setTotalAmount(new BigDecimal("150000"));
        return order;
    }

    private Payment capturePayment() {
        ArgumentCaptor<Payment> captor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).save(captor.capture());
        return captor.getValue();
    }
}
