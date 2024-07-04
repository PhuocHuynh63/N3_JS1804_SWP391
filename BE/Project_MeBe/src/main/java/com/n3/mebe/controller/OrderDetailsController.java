package com.n3.mebe.controller;

import com.n3.mebe.dto.request.order.details.OrderDetailsRequest;
import com.n3.mebe.dto.request.order.details.UpdateOrderDetailsRequest;
import com.n3.mebe.dto.response.order.details.OrderDetailsResponse;
import com.n3.mebe.entity.OrderDetail;
import com.n3.mebe.service.IOrderDetailsService;
import com.n3.mebe.service.iml.OrderDetailsService;
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
