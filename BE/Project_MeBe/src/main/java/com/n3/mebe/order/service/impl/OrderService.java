package com.n3.mebe.order.service.impl;

import com.n3.mebe.catalog.entity.ProductStatus;
import com.n3.mebe.order.entity.OrderStatus;
import com.n3.mebe.payment.entity.PaymentStatus;
import lombok.RequiredArgsConstructor;


import com.n3.mebe.order.dto.request.CancelOrderRequest;
import com.n3.mebe.order.dto.request.OrderRefundRequest;
import com.n3.mebe.order.dto.request.OrderRequest;
import com.n3.mebe.order.dto.request.OrderStatusRequest;
import com.n3.mebe.order.dto.request.OrderDetailsRequest;
import com.n3.mebe.order.dto.response.OrderResponse;
import com.n3.mebe.order.dto.response.OrderUserResponse;
import com.n3.mebe.user.entity.*;
import com.n3.mebe.catalog.entity.*;
import com.n3.mebe.order.entity.*;
import com.n3.mebe.payment.entity.*;
import com.n3.mebe.voucher.entity.*;
import com.n3.mebe.wishlist.entity.*;
import com.n3.mebe.shared.exception.AppException;
import com.n3.mebe.shared.exception.ErrorCode;
import com.n3.mebe.order.mapper.OrderMapper;
import com.n3.mebe.user.mapper.UserMapper;
import com.n3.mebe.user.repository.*;
import com.n3.mebe.catalog.repository.*;
import com.n3.mebe.order.repository.*;
import com.n3.mebe.payment.repository.*;
import com.n3.mebe.voucher.repository.*;
import com.n3.mebe.wishlist.repository.*;
import com.n3.mebe.order.service.IOrderService;
import com.n3.mebe.payment.service.IPaymentService;
import com.n3.mebe.notification.service.ISendMailService;
import com.n3.mebe.catalog.service.impl.ProductService;
import com.n3.mebe.user.service.impl.UserService;
import com.n3.mebe.shared.util.DataUtils;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    // Chỉ cho phép hủy đơn hàng khi đơn đang ở các trạng thái này
    private static final Set<OrderStatus> CANCELLABLE_STATUSES =
            EnumSet.of(OrderStatus.PENDING_CONFIRMATION, OrderStatus.PROCESSING, OrderStatus.AWAITING_PAYMENT);

    private final IOrderRepository orderRepository;

    private final IOrderDetailsRepository orderDetailsRepository;

    private final IAddressRepository addressRepository;

    private final IUserRepository iUserRepository;

    private final UserService userService;

    private final ProductService productService;

    private final ISendMailService sendMailService;

    private final IPaymentService paymentService;

    private final IProductRepository productRepository;

    private final IPaymentRepository paymentRepository;

    private final UserMapper userMapper;

    private final OrderMapper orderMapper;


    @Override
    public Order getOrder(int orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NO_EXIST));
    }


    private OrderUserResponse resolveOrderUser(Order order) {
        return order.getUser() != null ? userMapper.toOrderUserResponse(order.getUser()) : null;
    }

    // <editor-fold default state="collapsed" desc="save OrderDetails">
    private void saveOrderDetails(List<OrderDetailsRequest> items, Order order) {
        for (OrderDetailsRequest item : items) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);

            Product product = productService.getProductById(item.getProductId());
            //cộng số lượng đã bán
            int totalSold = product.getTotalSold() + item.getQuantity();
            product.setTotalSold(totalSold);
            productRepository.save(product);

            orderDetail.setProduct(product);
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setPrice(item.getPrice());
            orderDetail.setSalePrice(item.getSalePrice());
            orderDetailsRepository.save(orderDetail);
        }
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="save Guess User Address">
    private void saveGuessUserAddress(OrderRequest orderRequest, User user) {
        Address address = new Address();
        address.setUser(user);
        address.setTitle("Address");
        address.setAddress(orderRequest.getGuest().getAddress());
        addressRepository.save(address); // Save Address for guess user
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="cancelOrderDetails">
    private void cancelOrderDetails(List<OrderDetail> items) {
        for (OrderDetail item : items) {

            Product product = item.getProduct();
            //cộng số lượng đã bán
            int totalSold = product.getTotalSold() - item.getQuantity();
            product.setTotalSold(totalSold);
            //trả lại số lượng đã bán
            int quantity = product.getQuantity() + item.getQuantity();
            product.setQuantity(quantity);
            product.setStatus(ProductStatus.IN_STOCK);
            productRepository.save(product);
    }
    }// </editor-fold>



    /**
     *  Request from Client
     *
     */

    // <editor-fold default state="collapsed" desc="Create Orders">
    @Override
    @Transactional
    public boolean createOrder(OrderRequest orderRequest) {
        boolean check = false;

        User user = new User();
        Order order = new Order();

        // Neu khong phai la guest thi kiem User bang ID
        if (orderRequest.getGuest() != null){
            // lay guess tu request
            order.setUser(null);
            order.setFirstName(orderRequest.getGuest().getFirstName());
            order.setLastName(orderRequest.getGuest().getLastName());
            order.setEmail(orderRequest.getGuest().getEmail());
            order.setPhoneNumber(orderRequest.getGuest().getPhoneNumber());
        }else {
            user = userService.getUserById(orderRequest.getUserId());
            order.setUser(user);
            order.setFirstName(user.getFirstName());
            order.setLastName(user.getLastName());
            order.setEmail(user.getEmail());
            order.setPhoneNumber(user.getPhoneNumber());
        }


        // Chỉ chấp nhận trạng thái ban đầu "Chờ xác nhận" (COD) hoặc "Đang được xử lý" (VNPay)
        if (orderRequest.getStatus() == OrderStatus.PENDING_CONFIRMATION
                || orderRequest.getStatus() == OrderStatus.PROCESSING) {
            order.setStatus(orderRequest.getStatus());
        }
        //   order.setVoucher(); --> chua them vao


        String code_order;
        do {
            code_order = DataUtils.generateCode(8);
        } while (orderRepository.existsByOrderCode(code_order));
        // Mỗi đơn hàng có 1 code riêng
        order.setOrderCode(code_order);
        
        order.setShipAddress(orderRequest.getShipAddress());
        order.setTotalAmount(orderRequest.getTotalAmount());
        order.setOrderType(orderRequest.getOrderType());
        order.setPaymentStatus(orderRequest.getPaymentStatus());
        order.setNote(orderRequest.getNote());

        Date now = new Date();

        order.setCreatedAt(now);
        order.setUpdatedAt(now);
        orderRepository.save(order);

        saveOrderDetails(orderRequest.getItem(), order);
        paymentService.savePayment(order , orderRequest.getTransactionReference());
        sendMailService.createSendEmailVerifyOrder(order);
        check = true;


        return check;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Update Orders">
    @Override
    @Transactional
    public OrderResponse updateOrder(int orId, OrderRequest orderRequest) {
        Order order = getOrder(orId);

        // Guest: không tạo User mới (trước đây tạo User chưa lưu -> lỗi TransientPropertyValueException),
        // thông tin người nhận được lưu thẳng trên Order giống như createOrder
        if (orderRequest.getGuest() != null) {
            order.setUser(null);
            order.setFirstName(orderRequest.getGuest().getFirstName());
            order.setLastName(orderRequest.getGuest().getLastName());
            order.setEmail(orderRequest.getGuest().getEmail());
            order.setPhoneNumber(orderRequest.getGuest().getPhoneNumber());
        } else {
            order.setUser(userService.getUserById(orderRequest.getUserId()));
        }
        //   order.setVoucher(); --> chua them vao

        order.setStatus(orderRequest.getStatus());
        order.setShipAddress(orderRequest.getShipAddress());
        order.setTotalAmount(orderRequest.getTotalAmount());
        order.setOrderType(orderRequest.getOrderType());
        order.setPaymentStatus(orderRequest.getPaymentStatus());
        order.setNote(orderRequest.getNote());

        Date now = new Date();
        order.setUpdatedAt(now);

        Order saved = orderRepository.save(order);
        return orderMapper.toResponse(saved, resolveOrderUser(saved));
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="refund Orders">
    @Override
    @Transactional
    public OrderResponse refundOrder(OrderRefundRequest request) {
        Order order = orderRepository.findByOrderCode(request.getOrderCode());
        if (order == null) {
            throw new AppException(ErrorCode.ORDER_NO_EXIST);
        }


        //   order.setVoucher(); --> chua them vao
        order.setStatus(OrderStatus.REFUNDED);

        order.setTotalAmount(request.getTotalAmount());

        order.setNote(request.getNote());

        Date now = new Date();
        order.setUpdatedAt(now);
        saveOrderDetails(request.getOrderDetails() , order);
        Order saved = orderRepository.save(order);
        return orderMapper.toResponse(saved, resolveOrderUser(saved));
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Cancel Order">
    @Override
    @Transactional
    public String cancelOrder(int orderId, CancelOrderRequest request) {
        Order order = getOrder(orderId);
        OrderStatus status = order.getStatus();
        String msg = "";

        // Chỉ cho phép hủy đơn hàng khi đơn hàng đang ở trạng thái "Chờ xác nhận", "Đang được xử lý" hoặc "Đang thanh toán"
        if (!CANCELLABLE_STATUSES.contains(status)) {
            throw new AppException(ErrorCode.ORDER_NOT_CANCEL);
        }else {
            msg = "Hủy thành công";
            order.setStatus(OrderStatus.CANCELLED);
            Payment payment = paymentRepository.findByOrderOrderId(orderId);
            payment.setPaymentStatus(PaymentStatus.CANCELLED);
            paymentRepository.save(payment);
            order.setNote(request.getNote());
            List<OrderDetail> orderDetails = orderDetailsRepository.findByOrderOrderId(orderId);


            cancelOrderDetails(orderDetails);
            Date now = new Date();
            order.setUpdatedAt(now);

            orderRepository.save(order);
        }
        return msg;
    }
    // </editor-fold>


    @Override
    public void deleteOrder(String orderId) {
    }

    // <editor-fold default state="collapsed" desc="set Status Order">
    @Override
    public String setStatusOrder(OrderStatusRequest request) {
       Order order = getOrder(request.getOrderId());
       String msg = "";
       if (order.getStatus() == null) {
           return "Update Status không thành công";
       }

       // Nếu set status là đa giao thì cập nhập thanh toán thành công
        if (request.getStatus() == OrderStatus.DELIVERED) {
            order.setStatus(request.getStatus());
            order.setPaymentStatus(PaymentStatus.PAID);
            order.setUpdatedAt( new Date());
            Payment payment = paymentRepository.findByOrderOrderId(request.getOrderId());
            payment.setPaymentStatus(PaymentStatus.PAID);
            paymentRepository.save(payment);
        }else {
            order.setStatus(request.getStatus());
        }

       orderRepository.save(order);

       return "Update status " + request.getStatus().getLabel() + " thành công";
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
            orderResponseList.add(orderMapper.toResponse(order, resolveOrderUser(order)));
        }
        return orderResponseList;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get List Orders Email">
    @Override
    public List<OrderResponse> getOrdersListEmail(String email) {
        List<Order> list = orderRepository.findByUserEmail(email);
        List<OrderResponse> orderResponseList = new ArrayList<>();
        for (Order order : list) {
            orderResponseList.add(orderMapper.toResponse(order, resolveOrderUser(order)));
        }
        return orderResponseList;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get List Orders Phone">
    @Override
    public List<OrderResponse> getOrdersListPhone(String phone) {
        return orderRepository.findByUserPhoneNumber(phone)
                .stream()
                .map(order -> orderMapper.toResponse(order, resolveOrderUser(order)))
                .toList(); // Dùng .collect(Collectors.toList()) nếu bạn đang dùng Java bản cũ hơn 16
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get Order by orderId">
    @Override
    public OrderResponse getOrderResponse(int orId) {
        Order order = getOrder(orId);
        return orderMapper.toResponse(order, resolveOrderUser(order));
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get Order Code Response">
    @Override
    public OrderResponse getOrderCodeResponse(String code) {
        if(!orderRepository.existsByOrderCode(code)){
            throw new AppException(ErrorCode.ORDER_NO_EXIST);
        }
        Order order = orderRepository.findByOrderCode(code);
        return orderMapper.toResponse(order, resolveOrderUser(order));
    }// </editor-fold>


}
