package com.n3.mebe.order.service.impl;

import lombok.RequiredArgsConstructor;

import com.n3.mebe.order.mapper.OrderDetailsMapper;
import com.n3.mebe.order.dto.response.OrderDetailsResponse;
import com.n3.mebe.order.entity.OrderDetail;
import com.n3.mebe.order.repository.IOrderDetailsRepository;
import com.n3.mebe.order.service.IOrderDetailsService;
import com.n3.mebe.order.service.impl.OrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailsService implements IOrderDetailsService {

    private final OrderService orderService;

    private final IOrderDetailsRepository orderDetailsRepository;

    private final OrderDetailsMapper orderDetailsMapper;


    /**
     *  Response from Client
     *
     */


    // <editor-fold default state="collapsed" desc="get Order Details by Order ID">
    @Override
    public List<OrderDetailsResponse> getOrderDetailsById(int orderId) {

        List<OrderDetail> list = orderDetailsRepository.findByOrderOrderId(orderId);

        var orderResponse = orderService.getOrderResponse(orderId);
        List<OrderDetailsResponse> responses = new ArrayList<>();
        for (OrderDetail orderDetail : list) {
            responses.add(orderDetailsMapper.toResponse(orderDetail, orderResponse));
        }
        return responses;
    }// </editor-fold>


}
