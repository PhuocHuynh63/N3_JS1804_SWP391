package com.n3.mebe.order.service;

import com.n3.mebe.order.dto.request.CancelOrderRequest;
import com.n3.mebe.order.dto.request.OrderRefundRequest;
import com.n3.mebe.order.dto.request.OrderRequest;
import com.n3.mebe.order.dto.request.OrderStatusRequest;
import com.n3.mebe.order.dto.response.OrderResponse;
import com.n3.mebe.order.entity.Order;

import java.util.List;

public interface IOrderService {

    List<OrderResponse> getOrdersList();

    boolean createOrder(OrderRequest orderRequest);

    OrderResponse updateOrder(int orId, OrderRequest orderRequest);

    OrderResponse refundOrder(OrderRefundRequest request);

    String cancelOrder(int orderId , CancelOrderRequest cancelOrderRequest);

    Order getOrder(int orId);

    OrderResponse getOrderResponse(int orId);

    OrderResponse getOrderCodeResponse(String code);

    void deleteOrder(String orderId);

    String setStatusOrder(OrderStatusRequest request);

    List<OrderResponse> getOrdersListEmail(String email);

    List<OrderResponse> getOrdersListPhone(String phone);
}
