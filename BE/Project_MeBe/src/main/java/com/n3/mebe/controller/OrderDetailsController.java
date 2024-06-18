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
     *  Request from Client
     *
     */

    @PostMapping("/create")
    public String createOrderDetail(@RequestBody List<OrderDetailsRequest> request) {
        return orderDetailsService.createOrderDetail(request);
    }


    @PutMapping("/update/")
    public String updateOrderDetail(@RequestBody UpdateOrderDetailsRequest request) {
        return orderDetailsService.updateOrderDetail(request);
    }

    @PutMapping("/update_multiple/")
    public String updateOrderDetail(@RequestBody List<UpdateOrderDetailsRequest> request) {
        return orderDetailsService.updateMultipleOrderDetails(request);
    }



    /**
     *  Response from Client
     *
     */

    @GetMapping("/list")
    public List<OrderDetailsResponse> getListOrderDetails() {
        return orderDetailsService.getAllOrderDetails();
    }


    @GetMapping("/list/orderId={id}")
    public List<OrderDetailsResponse> getListOrderDetailsByOrderId(@PathVariable("id") int orderId) {
        return orderDetailsService.getOrderDetailsById(orderId);
    }



}
