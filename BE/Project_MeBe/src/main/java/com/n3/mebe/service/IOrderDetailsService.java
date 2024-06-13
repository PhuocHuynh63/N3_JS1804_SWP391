package com.n3.mebe.service;

import com.n3.mebe.dto.response.order.details.OrderDetailsResponse;

import java.util.List;

public interface IOrderDetailsService {

    List<OrderDetailsResponse> getAllOrderDetails();

    public List<OrderDetailsResponse> getOrderDetailsById(int orderId);
}
