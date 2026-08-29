package com.n3.mebe.mapper;

import com.n3.mebe.dto.response.user.GuestResponse;
import com.n3.mebe.entity.Order;
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
