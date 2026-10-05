package com.n3.mebe.order.mapper;

import com.n3.mebe.order.dto.response.OrderResponse;
import com.n3.mebe.order.dto.response.OrderUserResponse;
import com.n3.mebe.order.entity.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    private final ModelMapper modelMapper;

    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderResponse toResponse(Order order, OrderUserResponse userResponse) {
        OrderResponse response = modelMapper.map(order, OrderResponse.class);
        response.setUser(userResponse);
        return response;
    }
}
