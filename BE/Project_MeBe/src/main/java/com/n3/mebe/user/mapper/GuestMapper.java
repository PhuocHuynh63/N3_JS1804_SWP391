package com.n3.mebe.user.mapper;

import com.n3.mebe.user.dto.response.GuestResponse;
import com.n3.mebe.order.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class GuestMapper {

    public GuestResponse toGuestResponse(Order order) {
        GuestResponse response = new GuestResponse();
        response.setFirstName(order.getFirstName());
        response.setLastName(order.getLastName());
        response.setEmail(order.getEmail());
        response.setPhoneNumber(order.getPhoneNumber());
        return response;
    }
}
