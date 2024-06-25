package com.n3.mebe.service;

import com.n3.mebe.dto.request.order.details.OrderDetailsRequest;
import com.n3.mebe.dto.request.order.details.UpdateOrderDetailsRequest;
import com.n3.mebe.dto.response.order.details.OrderDetailsResponse;
import com.n3.mebe.entity.OrderDetail;

import java.util.List;

public interface IOrderDetailsService {

    

     String createOrderDetail(List<OrderDetailsRequest> request);

    String updateOrderDetail(UpdateOrderDetailsRequest request);

    String updateMultipleOrderDetails(List<UpdateOrderDetailsRequest> request);

     List<OrderDetailsResponse> getOrderDetailsById(int orderId);
}
