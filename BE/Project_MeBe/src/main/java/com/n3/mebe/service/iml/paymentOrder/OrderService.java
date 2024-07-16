package com.n3.mebe.service.iml.paymentOrder;


import com.n3.mebe.dto.request.order.CancelOrderRequest;
import com.n3.mebe.dto.request.order.OrderRequest;
import com.n3.mebe.dto.request.order.OrderStatusRequest;
import com.n3.mebe.dto.request.order.details.OrderDetailsRequest;
import com.n3.mebe.dto.response.order.OrderResponse;
import com.n3.mebe.dto.response.order.OrderUserResponse;
import com.n3.mebe.dto.response.user.UserAddressResponse;
import com.n3.mebe.entity.*;
import com.n3.mebe.exception.AppException;
import com.n3.mebe.exception.ErrorCode;
import com.n3.mebe.repository.*;
import com.n3.mebe.service.IOrderService;
import com.n3.mebe.service.IPaymentService;
import com.n3.mebe.service.ISendMailService;
import com.n3.mebe.service.iml.ProductService;
import com.n3.mebe.service.iml.UserService;
import com.n3.mebe.util.DataUtils;
import jakarta.transaction.Transactional;
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
    private IOrderDetailsRepository orderDetailsRepository;

    @Autowired
    private IAddressRepository addressRepository;

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ISendMailService sendMailService;

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private IProductRespository productRespository;

    @Autowired
    private IPaymentRepository paymentRepository;


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

    // <editor-fold default state="collapsed" desc="save OrderDetails">
    private void saveOrderDetails(List<OrderDetailsRequest> items, Order order) {
        for (OrderDetailsRequest item : items) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);

            Product product = productService.getProductById(item.getProductId());
            //cộng số lượng đã bán
            int totalSold = product.getTotalSold() + item.getQuantity();
            product.setTotalSold(totalSold);
            productRespository.save(product);

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
            productRespository.save(product);
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
        String status = "Chờ xác nhận"; // trạng thái đầu tiên khi mới tạo order

        // Neu khong phai la guest thi kiem User bang ID
        if (orderRequest.getGuest() != null){
            boolean checkEmail = iUserRepository.existsByEmail(orderRequest.getGuest().getEmail());
            if(!checkEmail){
                String roll = "guest";
                // lay guess tu request de tao ra USER moi
                user.setFirstName(orderRequest.getGuest().getFirstName());
                user.setLastName(orderRequest.getGuest().getLastName());
                user.setEmail(orderRequest.getGuest().getEmail());
                user.setBirthOfDate(orderRequest.getGuest().getBirthOfDate());
                user.setPhoneNumber(orderRequest.getGuest().getPhoneNumber());
                user.setRole(roll);
                iUserRepository.save(user);
                //save địa chỉ của guess
                saveGuessUserAddress(orderRequest, user);
            }else {
                user = iUserRepository.findByEmail(orderRequest.getGuest().getEmail());
            }
        }else {
            user = userService.getUserById(orderRequest.getUserId());

        }

        order.setUser(user);
        if(orderRequest.getStatus() != null){
            if(orderRequest.getStatus().equals("Đang được xử lý")){
                order.setStatus(orderRequest.getStatus());
            }else if(orderRequest.getStatus().equals("Chờ xác nhận")){
                order.setStatus(status);
            }
        }
        //   order.setVoucher(); --> chua them vao


        order.setStatus(status);

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
        sendMailService.createSendEmailVerifyOrder(user.getEmail(), order);
        check = true;


        return check;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Update Orders">
    @Override
    @Transactional
    public Order updateOrder(int orId, OrderRequest orderRequest) {
        Order order = getOrder(orId);
        User user =  new User();

        // Neu khong phai la guess thi kiem User bang ID
        if (orderRequest.getGuest() != null){
            String roll = "guest";

            // lay guess tu request de tao ra USER moi
            user.setFirstName(orderRequest.getGuest().getFirstName());
            user.setLastName(orderRequest.getGuest().getLastName());
            user.setEmail(orderRequest.getGuest().getEmail());
            user.setBirthOfDate(orderRequest.getGuest().getBirthOfDate());
            user.setPhoneNumber(orderRequest.getGuest().getPhoneNumber());
            user.setRole(roll);
        }else {
            user = userService.getUserById(orderRequest.getUserId());
        }

        order.setUser(user);
        //   order.setVoucher(); --> chua them vao

        order.setStatus(orderRequest.getStatus());
        order.setShipAddress(orderRequest.getShipAddress());
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
        if (!status.equals("Chờ xác nhận") && !status.equals("Đang được xử lý") && !status.equals("Đang thanh toán")) {
            throw new AppException(ErrorCode.ORDER_NOT_CANCEL);
        }else {
            status = "Đã hủy";
            msg = "Hủy thành công";
            order.setStatus(status);
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
       String status = "Đã thanh toán";
       if(order.getStatus().isEmpty()){
           return "Update Status không thành công";
       }

       // Nếu set status là đa giao thì cập nhập thanh toán thành công
        if(request.equals("Đã giao")){
            order.setStatus(request.getStatus());
            order.setPaymentStatus(status);
            Payment payment = paymentRepository.findByOrderOrderId(request.getOrderId());
            payment.setPaymentStatus(status);
        }else {
            order.setStatus(request.getStatus());
        }

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
            orderResponse.setOrderCode(order.getOrderCode());
            orderResponse.setShipAddress(order.getShipAddress());

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

    // <editor-fold default state="collapsed" desc="Get List Orders Email">
    @Override
    public List<OrderResponse> getOrdersListEmail(String email) {
        List<Order> list = orderRepository.findByUserEmail(email);
        List<OrderResponse> orderResponseList = new ArrayList<>();

        for (Order order : list) {
            OrderResponse orderResponse = new OrderResponse();

            orderResponse.setOrderId(order.getOrderId());
            orderResponse.setUser(getUserByIdResponse(order.getUser().getUserId()));
            orderResponse.setVoucher(order.getVoucher());
            orderResponse.setStatus(order.getStatus());
            orderResponse.setOrderCode(order.getOrderCode());
            orderResponse.setShipAddress(order.getShipAddress());

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

    // <editor-fold default state="collapsed" desc="Get List Orders Phone">
    @Override
    public List<OrderResponse> getOrdersListPhone(String phone) {
        List<Order> list = orderRepository.findByUserPhoneNumber(phone);
        List<OrderResponse> orderResponseList = new ArrayList<>();

        for (Order order : list) {
            OrderResponse orderResponse = new OrderResponse();

            orderResponse.setOrderId(order.getOrderId());
            orderResponse.setUser(getUserByIdResponse(order.getUser().getUserId()));
            orderResponse.setVoucher(order.getVoucher());
            orderResponse.setStatus(order.getStatus());
            orderResponse.setOrderCode(order.getOrderCode());
            orderResponse.setShipAddress(order.getShipAddress());

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
        orderResponse.setOrderCode(order.getOrderCode());
        orderResponse.setShipAddress(order.getShipAddress());

        orderResponse.setTotalAmount(order.getTotalAmount());

        orderResponse.setOrderType(order.getOrderType());
        orderResponse.setPaymentStatus(order.getPaymentStatus());
        orderResponse.setNote(order.getNote());
        orderResponse.setCreatedAt(order.getCreatedAt());
        orderResponse.setUpdatedAt(order.getUpdatedAt());

        return orderResponse;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get Order Code Response">
    @Override
    public OrderResponse getOrderCodeResponse(String code) {
        if(!orderRepository.existsByOrderCode(code)){
            throw new AppException(ErrorCode.ORDER_NO_EXIST);
        }
        Order order = orderRepository.findByOrderCode(code);

        OrderResponse orderResponse = new OrderResponse();

        orderResponse.setOrderId(order.getOrderId());


        orderResponse.setUser(getUserByIdResponse(order.getUser().getUserId()));
        orderResponse.setVoucher(order.getVoucher());
        orderResponse.setStatus(order.getStatus());
        orderResponse.setOrderCode(order.getOrderCode());
        orderResponse.setShipAddress(order.getShipAddress());

        orderResponse.setTotalAmount(order.getTotalAmount());

        orderResponse.setOrderType(order.getOrderType());
        orderResponse.setPaymentStatus(order.getPaymentStatus());
        orderResponse.setNote(order.getNote());
        orderResponse.setCreatedAt(order.getCreatedAt());
        orderResponse.setUpdatedAt(order.getUpdatedAt());
        return orderResponse;
    }// </editor-fold>


}
