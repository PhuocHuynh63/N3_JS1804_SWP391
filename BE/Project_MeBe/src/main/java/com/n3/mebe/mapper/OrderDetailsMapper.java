package com.n3.mebe.mapper;

import com.n3.mebe.dto.response.order.OrderResponse;
import com.n3.mebe.dto.response.order.details.OrderDetailsResponse;
import com.n3.mebe.entity.OrderDetail;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailsMapper {

    public OrderDetailsResponse toResponse(OrderDetail orderDetail, OrderResponse orderResponse) {
        OrderDetailsResponse response = new OrderDetailsResponse();
        response.setOdId(orderDetail.getOdId());
        response.setOrder(orderResponse);
        response.setProduct(orderDetail.getProduct());
        response.setQuantity(orderDetail.getQuantity());
        response.setPrice(orderDetail.getPrice());
        response.setSalePrice(orderDetail.getSalePrice());
        return response;
    }
}
