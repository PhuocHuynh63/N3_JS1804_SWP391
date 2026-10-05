package com.n3.mebe.order.controller;

import com.n3.mebe.order.dto.request.OrderDetailsRequest;
import com.n3.mebe.order.dto.request.UpdateOrderDetailsRequest;
import com.n3.mebe.order.dto.response.OrderDetailsResponse;
import com.n3.mebe.order.entity.OrderDetail;
import com.n3.mebe.order.service.IOrderDetailsService;
import com.n3.mebe.order.service.impl.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/order_details")
public class OrderDetailsController {

    @Autowired
    private IOrderDetailsService orderDetailsService;

    /**
     * Response from Client
     *
     */

    @GetMapping("/list/orderId={id}")
    List<OrderDetailsResponse> getListOrderDetailsByOrderId(@PathVariable("id") int orderId) {
        return orderDetailsService.getOrderDetailsById(orderId);
    }

}
