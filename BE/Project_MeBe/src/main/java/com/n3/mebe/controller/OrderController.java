package com.n3.mebe.controller;

import com.n3.mebe.dto.request.order.CancelOrderRequest;
import com.n3.mebe.dto.request.order.OrderRequest;
import com.n3.mebe.dto.request.order.OrderStatusRequest;
import com.n3.mebe.dto.request.product.ProductRequest;
import com.n3.mebe.dto.response.order.OrderResponse;
import com.n3.mebe.entity.Order;
import com.n3.mebe.entity.Product;
import com.n3.mebe.service.IOrderService;
import com.n3.mebe.service.iml.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;


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
    public String cancelOrder(@PathVariable("id") int id, @RequestBody CancelOrderRequest request) {
        return orderService.cancelOrder(id, request);
    }

    //Cancel order by id
    @PutMapping("/status")
    public String cancelOrder(@RequestBody OrderStatusRequest request) {
        return orderService.setStatusOrder(request);
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
