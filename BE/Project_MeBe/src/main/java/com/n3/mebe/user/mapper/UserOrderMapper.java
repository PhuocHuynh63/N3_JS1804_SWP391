package com.n3.mebe.user.mapper;

import com.n3.mebe.user.dto.response.UserOrderResponse;
import com.n3.mebe.order.entity.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserOrderMapper {

    private final ModelMapper modelMapper;

    public UserOrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserOrderResponse toUserOrderResponse(Order order) {
        return modelMapper.map(order, UserOrderResponse.class);
    }

    public List<UserOrderResponse> toUserOrderResponseList(List<Order> orders) {
        return orders.stream().map(this::toUserOrderResponse).toList();
    }
}
