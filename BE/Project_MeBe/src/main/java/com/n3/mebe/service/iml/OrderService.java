package com.n3.mebe.service.iml;


import com.n3.mebe.dto.request.order.OrderRequest;
import com.n3.mebe.dto.response.order.OrderResponse;
import com.n3.mebe.entity.Order;
import com.n3.mebe.entity.User;
import com.n3.mebe.exception.AppException;
import com.n3.mebe.exception.ErrorCode;
import com.n3.mebe.repository.IOrderRepository;
import com.n3.mebe.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private UserService userService;

    public Order getOrder(int orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NO_EXIST));
    }

    /**
     *  Request from Client
     *
     */

    // <editor-fold default state="collapsed" desc="Create Orders">
    @Override
    public Order createOrder(OrderRequest orderRequest) {
        User user = new User();
        Order order = new Order();

        // Neu khong phai la guess thi kiem User bang ID
        if (orderRequest.getGuess() != null){
            String roll = "guess";

            // lay guess tu request de tao ra USER moi
            user.setFirstName(orderRequest.getGuess().getFirstName());
            user.setLastName(orderRequest.getGuess().getLastName());
            user.setEmail(orderRequest.getGuess().getEmail());
            user.setBirthOfDate(orderRequest.getGuess().getBirthOfDate());
            user.setPhoneNumber(orderRequest.getGuess().getPhoneNumber());
            user.setRole(roll);
        }else {
            user = userService.getUserById(orderRequest.getUserId());
        }

        order.setUser(user);
        //   order.setVoucher(); --> chua them vao

        order.setStatus(orderRequest.getStatus());
        order.setDeliveryFee(orderRequest.getDeliveryFee());
        order.setTotalAmount(orderRequest.getTotalAmount());
        order.setOrderType(orderRequest.getOrderType());
        order.setPaymentStatus(orderRequest.getPaymentStatus());
        order.setNote(orderRequest.getNote());

        Date now = new Date();

        order.setCreatedAt(now);
        order.setUpdatedAt(now);

        return orderRepository.save(order);
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Update Orders">
    @Override
    public Order updateOrder(int orId, OrderRequest orderRequest) {
        Order order = getOrder(orId);
        User user =  new User();


        // Neu khong phai la guess thi kiem User bang ID
        if (orderRequest.getGuess() != null){
            String roll = "guess";

            // lay guess tu request de tao ra USER moi
            user.setFirstName(orderRequest.getGuess().getFirstName());
            user.setLastName(orderRequest.getGuess().getLastName());
            user.setEmail(orderRequest.getGuess().getEmail());
            user.setBirthOfDate(orderRequest.getGuess().getBirthOfDate());
            user.setPhoneNumber(orderRequest.getGuess().getPhoneNumber());
            user.setRole(roll);
        }else {
            user = userService.getUserById(orderRequest.getUserId());
        }

        order.setUser(user);
        //   order.setVoucher(); --> chua them vao

        order.setStatus(orderRequest.getStatus());
        order.setDeliveryFee(orderRequest.getDeliveryFee());
        order.setTotalAmount(orderRequest.getTotalAmount());
        order.setOrderType(orderRequest.getOrderType());
        order.setPaymentStatus(orderRequest.getPaymentStatus());
        order.setNote(orderRequest.getNote());

        Date now = new Date();
        order.setUpdatedAt(now);

        return orderRepository.save(order);
    }// </editor-fold>


    @Override
    public void deleteOrder(String orderId) {
    }


    /**
     *  Response from Client
     *
     */

    // <editor-fold default state="collapsed" desc="Get List Orders">
    @Override
    public List<OrderResponse> getOrdersList() {
        List<Order> list = orderRepository.findAll();
        List<OrderResponse> orderResponseList = new ArrayList<>();

        for (Order order : list) {
            OrderResponse orderResponse = new OrderResponse();

            orderResponse.setOrderId(order.getOrderId());
            orderResponse.setUser(order.getUser());
            orderResponse.setVoucher(order.getVoucher());
            orderResponse.setStatus(order.getStatus());
            orderResponse.setDeliveryFee(order.getDeliveryFee());
            orderResponse.setTotalAmount(order.getTotalAmount());
            orderResponse.setDepositeAmount(order.getDepositeAmount());
            orderResponse.setOrderType(order.getOrderType());
            orderResponse.setPaymentStatus(order.getPaymentStatus());
            orderResponse.setNote(order.getNote());
            orderResponse.setCreatedAt(order.getCreatedAt());
            orderResponse.setUpdatedAt(order.getUpdatedAt());

            orderResponseList.add(orderResponse);
        }

        return orderResponseList;
    }// </editor-fold>
}
