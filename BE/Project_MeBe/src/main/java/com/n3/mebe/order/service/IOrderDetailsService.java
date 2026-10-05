package com.n3.mebe.order.service;

import com.n3.mebe.order.dto.request.OrderDetailsRequest;
import com.n3.mebe.order.dto.request.UpdateOrderDetailsRequest;
import com.n3.mebe.order.dto.response.OrderDetailsResponse;
import com.n3.mebe.order.entity.OrderDetail;

import java.util.List;

public interface IOrderDetailsService {

     List<OrderDetailsResponse> getOrderDetailsById(int orderId);
}
