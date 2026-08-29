package com.n3.mebe.mapper;

import com.n3.mebe.dto.response.order.OrderResponse;
import com.n3.mebe.dto.response.order.OrderUserResponse;
import com.n3.mebe.entity.Order;
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
