package com.n3.mebe.controller;

import com.n3.mebe.dto.request.order.CancelOrderRequest;
import com.n3.mebe.dto.request.order.OrderRequest;
import com.n3.mebe.dto.request.product.ProductRequest;
import com.n3.mebe.dto.response.order.OrderResponse;
import com.n3.mebe.entity.Order;
import com.n3.mebe.entity.Product;
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

    //Create order
    @PostMapping("/create")
    public Order createOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }


    //Update order by id
    @PutMapping("/update/orId={id}")
    public Order updateOrder(@PathVariable("id") int orId, @RequestBody OrderRequest orderRequest) {
        return orderService.updateOrder(orId , orderRequest);
    }

    //Cancel order by id
    @PutMapping("/cancel/orId={id}")
    public Order cancelOrder(@PathVariable("id") int id, @RequestBody CancelOrderRequest request) {
        return orderService.cancelOrder(id, request);
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
