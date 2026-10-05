package com.n3.mebe.order.controller;

import lombok.RequiredArgsConstructor;

import com.n3.mebe.order.dto.request.OrderDetailsRequest;
import com.n3.mebe.order.dto.request.UpdateOrderDetailsRequest;
import com.n3.mebe.order.dto.response.OrderDetailsResponse;
import com.n3.mebe.order.entity.OrderDetail;
import com.n3.mebe.order.service.IOrderDetailsService;
import com.n3.mebe.order.service.impl.OrderDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order_details")
@RequiredArgsConstructor
public class OrderDetailsController {

    private final IOrderDetailsService orderDetailsService;

    /**
     * Response from Client
     *
     */

    @GetMapping("/list/orderId={id}")
    List<OrderDetailsResponse> getListOrderDetailsByOrderId(@PathVariable("id") int orderId) {
        return orderDetailsService.getOrderDetailsById(orderId);
    }

}
