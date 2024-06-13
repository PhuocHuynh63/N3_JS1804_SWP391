package com.n3.mebe.controller;

import com.n3.mebe.dto.request.order.OrderRequest;
import com.n3.mebe.dto.response.order.OrderResponse;
import com.n3.mebe.entity.Order;
import com.n3.mebe.service.iml.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;



    /**
     *  Request from Client
     *
     */

    @PostMapping("/create")
    public Order createOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @PutMapping("/update/orId={id}")
    public Order updateOrder(@PathVariable("id") int orId, @RequestBody OrderRequest orderRequest) {
        return orderService.updateOrder(orId , orderRequest);
    }




    /**
     *  Response to Client
     *
     */

    @GetMapping("/list")
    public List<OrderResponse> getOrdersList() {
        return orderService.getOrdersList();
    }


}
