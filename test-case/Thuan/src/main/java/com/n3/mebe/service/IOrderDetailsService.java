package com.n3.mebe.service;

import com.n3.mebe.dto.request.order.details.OrderDetailsRequest;
import com.n3.mebe.dto.request.order.details.UpdateOrderDetailsRequest;
import com.n3.mebe.dto.response.order.details.OrderDetailsResponse;
import com.n3.mebe.entity.OrderDetail;

import java.util.List;

public interface IOrderDetailsService {

     List<OrderDetailsResponse> getOrderDetailsById(int orderId);
}
