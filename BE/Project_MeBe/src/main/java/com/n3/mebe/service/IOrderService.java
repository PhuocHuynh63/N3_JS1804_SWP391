package com.n3.mebe.service;

import com.n3.mebe.dto.request.order.OrderRequest;
import com.n3.mebe.dto.response.order.OrderResponse;
import com.n3.mebe.entity.Order;

import java.util.List;

public interface IOrderService {

    List<OrderResponse> getOrdersList();

    Order createOrder(OrderRequest orderRequest);
    Order updateOrder(int orId, OrderRequest orderRequest);

    void deleteOrder(String orderId);
}
