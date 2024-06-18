package com.n3.mebe.controller;


import com.n3.mebe.dto.request.order.details.OrderDetailsRequest;
import com.n3.mebe.dto.response.order.details.OrderDetailsResponse;
import com.n3.mebe.entity.OrderDetail;
import com.n3.mebe.service.iml.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/order_details")
public class OrderDetailsController {


    @Autowired
    private OrderDetailsService orderDetailsService;

    

    @PostMapping("/create")
    public OrderDetail createOrderDetail(@RequestBody OrderDetailsRequest request) {
        return orderDetailsService.createOrderDetail(request);
    }


    @PostMapping("/update/")
    public OrderDetail updateOrderDetail(@RequestBody OrderDetailsRequest request) {
        return orderDetailsService.createOrderDetail(request);
    }

    @GetMapping("/list")
    public List<OrderDetailsResponse> getListOrderDetails() {
        return orderDetailsService.getAllOrderDetails();
    }


    @GetMapping("/list/orderId={id}")
    public List<OrderDetailsResponse> getListOrderDetailsByOrderId(@PathVariable("id") int orderId) {
        return orderDetailsService.getOrderDetailsById(orderId);
    }



}
