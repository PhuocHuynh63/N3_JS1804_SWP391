package com.n3.mebe.service;

import com.n3.mebe.dto.request.order.CancelOrderRequest;
import com.n3.mebe.dto.request.order.OrderRefundRequest;
import com.n3.mebe.dto.request.order.OrderRequest;
import com.n3.mebe.dto.request.order.OrderStatusRequest;
import com.n3.mebe.dto.response.order.OrderResponse;
import com.n3.mebe.entity.Order;

import java.util.List;

public interface IOrderService {

    List<OrderResponse> getOrdersList();

    boolean createOrder(OrderRequest orderRequest);

    Order updateOrder(int orId, OrderRequest orderRequest);

    Order refundOrder(OrderRefundRequest request);

    String cancelOrder(int orderId , CancelOrderRequest cancelOrderRequest);

    Order getOrder(int orId);

    OrderResponse getOrderResponse(int orId);

    OrderResponse getOrderCodeResponse(String code);

    void deleteOrder(String orderId);

    String setStatusOrder(OrderStatusRequest request);

    List<OrderResponse> getOrdersListEmail(String email);

    List<OrderResponse> getOrdersListPhone(String phone);
}
