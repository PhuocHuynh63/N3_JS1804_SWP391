package com.n3.mebe.order.mapper;

import com.n3.mebe.catalog.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import com.n3.mebe.order.dto.response.OrderResponse;
import com.n3.mebe.order.dto.response.OrderDetailsResponse;
import com.n3.mebe.order.entity.OrderDetail;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderDetailsMapper {

    private final ProductMapper productMapper;

    public OrderDetailsResponse toResponse(OrderDetail orderDetail, OrderResponse orderResponse) {
        OrderDetailsResponse response = new OrderDetailsResponse();
        response.setOdId(orderDetail.getOdId());
        response.setOrder(orderResponse);
        response.setProduct(productMapper.toSummary(orderDetail.getProduct()));
        response.setQuantity(orderDetail.getQuantity());
        response.setPrice(orderDetail.getPrice());
        response.setSalePrice(orderDetail.getSalePrice());
        return response;
    }
}
