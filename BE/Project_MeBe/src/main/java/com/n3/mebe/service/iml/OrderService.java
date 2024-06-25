package com.n3.mebe.service.iml;


import com.n3.mebe.dto.request.order.CancelOrderRequest;
import com.n3.mebe.dto.request.order.OrderRequest;
import com.n3.mebe.dto.request.order.OrderStatusRequest;
import com.n3.mebe.dto.response.order.OrderResponse;
import com.n3.mebe.dto.response.order.OrderUserResponse;
import com.n3.mebe.dto.response.user.UserAddressResponse;
import com.n3.mebe.dto.response.user.UserOrderResponse;
import com.n3.mebe.dto.response.user.UserResponse;
import com.n3.mebe.entity.Order;
import com.n3.mebe.entity.User;
import com.n3.mebe.exception.AppException;
import com.n3.mebe.exception.ErrorCode;
import com.n3.mebe.repository.IOrderRepository;
import com.n3.mebe.service.IOrderService;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
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

    @Override
    public Order getOrder(int orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NO_EXIST));
    }


    // <editor-fold default state="collapsed" desc="Get UserOrderResponse By Id Response">
    public OrderUserResponse getUserByIdResponse(int id){

        OrderUserResponse userResponse = new OrderUserResponse();

        User user = userService.getUserById(id);

        userResponse.setId(user.getUserId());
        userResponse.setAvatar(user.getAvatar());
        userResponse.setUsername(user.getUsername());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setRole(user.getRole());
        userResponse.setBirthOfDate(user.getBirthOfDate());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setPoint(user.getPoint());

        List<UserAddressResponse> addressResponses = userService.getUserAddresses(user.getUserId());
        userResponse.setListAddress(addressResponses);

        userResponse.setCreateAt(user.getCreateAt());
        userResponse.setUpdateAt(user.getUpdateAt());
        userResponse.setDeleteAt(user.getDeleteAt());

        return userResponse;
    }// </editor-fold>


    /**
     *  Request from Client
     *
     */

    // <editor-fold default state="collapsed" desc="Create Orders">
    @Override
    @Transactional
    public Order createOrder(OrderRequest orderRequest) {
        User user = new User();
        Order order = new Order();
        String status = "Pending"; // trạng thái đầu tiên khi mới tạo order

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

        order.setStatus(status);


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
    @Transactional
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

        order.setTotalAmount(orderRequest.getTotalAmount());
        order.setOrderType(orderRequest.getOrderType());
        order.setPaymentStatus(orderRequest.getPaymentStatus());
        order.setNote(orderRequest.getNote());

        Date now = new Date();
        order.setUpdatedAt(now);

        return orderRepository.save(order);
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Cancel Order">
    @Override
    @Transactional
    public String cancelOrder(int orderId, CancelOrderRequest request) {
        Order order = getOrder(orderId);
        String status = order.getStatus();
        String msg = "";
        if (!status.equals("Pending") && !status.equals("Processing") && !status.equals("Awaiting Payment")) {
            throw new AppException(ErrorCode.ORDER_NOT_CANCEL);
        }else {
            status = "Canceled";
            msg = "Hủy thành công";
            order.setStatus(status);

            Date now = new Date();
            order.setUpdatedAt(now);

            orderRepository.save(order);
        }
        return msg;
    }// </editor-fold>


    @Override
    public void deleteOrder(String orderId) {
    }


    // <editor-fold default state="collapsed" desc="Confirm Order">
    @Override
    public String setStatusOrder(OrderStatusRequest request) {
       Order order = getOrder(request.getOrderId());
       String msg = "";
       if(order.getStatus().isEmpty()){
           return "Update Status không thành công";
       }
       order.setStatus(request.getStatus());
       orderRepository.save(order);

       return "Update status "+ request.getStatus() + "thành công";
    }// </editor-fold>


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
            orderResponse.setUser(getUserByIdResponse(order.getUser().getUserId()));
            orderResponse.setVoucher(order.getVoucher());
            orderResponse.setStatus(order.getStatus());

            orderResponse.setTotalAmount(order.getTotalAmount());

            orderResponse.setOrderType(order.getOrderType());
            orderResponse.setPaymentStatus(order.getPaymentStatus());
            orderResponse.setNote(order.getNote());
            orderResponse.setCreatedAt(order.getCreatedAt());
            orderResponse.setUpdatedAt(order.getUpdatedAt());

            orderResponseList.add(orderResponse);
        }

        return orderResponseList;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get Order by orderId">
    @Override
    public OrderResponse getOrderResponse(int orId) {
        Order order = getOrder(orId);

        OrderResponse orderResponse = new OrderResponse();

        orderResponse.setOrderId(order.getOrderId());


        orderResponse.setUser(getUserByIdResponse(order.getUser().getUserId()));
        orderResponse.setVoucher(order.getVoucher());
        orderResponse.setStatus(order.getStatus());

        orderResponse.setTotalAmount(order.getTotalAmount());

        orderResponse.setOrderType(order.getOrderType());
        orderResponse.setPaymentStatus(order.getPaymentStatus());
        orderResponse.setNote(order.getNote());
        orderResponse.setCreatedAt(order.getCreatedAt());
        orderResponse.setUpdatedAt(order.getUpdatedAt());

        return orderResponse;
    }// </editor-fold>





}
