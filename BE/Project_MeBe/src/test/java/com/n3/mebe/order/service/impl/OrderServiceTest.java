package com.n3.mebe.order.service.impl;

import com.n3.mebe.catalog.entity.Product;
import com.n3.mebe.catalog.entity.ProductStatus;
import com.n3.mebe.catalog.repository.IProductRepository;
import com.n3.mebe.order.dto.request.CancelOrderRequest;
import com.n3.mebe.order.entity.Order;
import com.n3.mebe.order.entity.OrderDetail;
import com.n3.mebe.order.entity.OrderStatus;
import com.n3.mebe.order.repository.IOrderDetailsRepository;
import com.n3.mebe.order.repository.IOrderRepository;
import com.n3.mebe.payment.entity.Payment;
import com.n3.mebe.payment.entity.PaymentStatus;
import com.n3.mebe.payment.repository.IPaymentRepository;
import com.n3.mebe.shared.exception.AppException;
import com.n3.mebe.shared.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    private static final int ORDER_ID = 1;

    @Mock
    private IOrderRepository orderRepository;
    @Mock
    private IOrderDetailsRepository orderDetailsRepository;
    @Mock
    private IProductRepository productRepository;
    @Mock
    private IPaymentRepository paymentRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void cancelOrder_pendingOrder_cancelsPaymentAndRestocksProducts() {
        Order order = order(OrderStatus.PENDING_CONFIRMATION);
        Payment payment = new Payment();
        Product product = product(0, 5, ProductStatus.OUT_OF_STOCK);
        OrderDetail detail = new OrderDetail();
        detail.setProduct(product);
        detail.setQuantity(2);

        when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.of(order));
        when(paymentRepository.findByOrderOrderId(ORDER_ID)).thenReturn(payment);
        when(orderDetailsRepository.findByOrderOrderId(ORDER_ID)).thenReturn(List.of(detail));

        CancelOrderRequest request = new CancelOrderRequest();
        request.setNote("Đổi ý");
        String msg = orderService.cancelOrder(ORDER_ID, request);

        assertThat(msg).isEqualTo("Hủy thành công");
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCELLED);
        assertThat(order.getNote()).isEqualTo("Đổi ý");
        assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.CANCELLED);
        assertThat(product.getQuantity()).isEqualTo(2);
        assertThat(product.getTotalSold()).isEqualTo(3);
        assertThat(product.getStatus()).isEqualTo(ProductStatus.IN_STOCK);
        verify(orderRepository).save(order);
    }

    @Test
    void cancelOrder_deliveredOrder_isRejected() {
        when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.of(order(OrderStatus.DELIVERED)));

        assertThatThrownBy(() -> orderService.cancelOrder(ORDER_ID, new CancelOrderRequest()))
                .isInstanceOf(AppException.class)
                .extracting(e -> ((AppException) e).getErrorCode())
                .isEqualTo(ErrorCode.ORDER_NOT_CANCEL);
        verify(orderRepository, never()).save(any());
    }

    @Test
    void getOrder_missingOrder_throwsOrderNoExist() {
        when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.getOrder(ORDER_ID))
                .isInstanceOf(AppException.class)
                .extracting(e -> ((AppException) e).getErrorCode())
                .isEqualTo(ErrorCode.ORDER_NO_EXIST);
    }

    private Order order(OrderStatus status) {
        Order order = new Order();
        order.setOrderId(ORDER_ID);
        order.setStatus(status);
        return order;
    }

    private Product product(int quantity, int totalSold, ProductStatus status) {
        Product product = new Product();
        product.setQuantity(quantity);
        product.setTotalSold(totalSold);
        product.setStatus(status);
        return product;
    }
}
