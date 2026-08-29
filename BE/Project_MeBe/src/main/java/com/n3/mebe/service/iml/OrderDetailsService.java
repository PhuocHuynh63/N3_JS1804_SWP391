package com.n3.mebe.service.iml;

import com.n3.mebe.mapper.OrderDetailsMapper;
import com.n3.mebe.dto.response.order.details.OrderDetailsResponse;
import com.n3.mebe.entity.OrderDetail;
import com.n3.mebe.repository.IOrderDetailsRepository;
import com.n3.mebe.service.IOrderDetailsService;
import com.n3.mebe.service.iml.paymentOrder.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailsService implements IOrderDetailsService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private IOrderDetailsRepository orderDetailsRepository;

    @Autowired
    private OrderDetailsMapper orderDetailsMapper;


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
