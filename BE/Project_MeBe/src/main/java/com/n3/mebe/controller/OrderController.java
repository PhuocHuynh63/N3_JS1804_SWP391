package com.n3.mebe.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.n3.mebe.dto.TransactionStatusDTO;
import com.n3.mebe.dto.request.order.CancelOrderRequest;
import com.n3.mebe.dto.request.order.OrderRequest;
import com.n3.mebe.dto.request.order.OrderStatusRequest;
import com.n3.mebe.dto.response.order.OrderResponse;
import com.n3.mebe.entity.Order;
import com.n3.mebe.service.IOrderDetailsService;
import com.n3.mebe.service.IOrderService;
import com.n3.mebe.service.iml.ProductService;
import com.n3.mebe.service.iml.paymentOrder.VNPayService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderDetailsService orderDetailsService;

    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private ProductService productService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * Request from Client
     *
     */

    // Create order
    @GetMapping("/create_vnpay")
    public void createOrderByVNPay(
            @RequestParam Map<String, String> vnp_Params,
            @RequestParam("orderRequestId") String orderRequestId,
            HttpServletResponse response) throws IOException {

        // Lấy OrderRequest từ Redis
        String orderRequestKey = "orderRequest:" + orderRequestId;
        String orderRequestJson = stringRedisTemplate.opsForValue().get(orderRequestKey);
        if (orderRequestJson == null) {
            // Xử lý khi không tìm thấy OrderRequest
            response.sendRedirect("http://14.225.253.116/order-error?message=Order request not found or expired");
            return;
        }

        OrderRequest orderRequest;
        try {
            orderRequest = new ObjectMapper().readValue(orderRequestJson, OrderRequest.class);
        } catch (JsonProcessingException e) {
            // Xử lý khi không thể parse OrderRequest
            response.sendRedirect("http://14.225.253.116/order-error?message=Failed to parse order request");
            return;
        }

        // Lấy paymentId từ params
        String paymentId = vnp_Params.get("vnp_TxnRef");
        String transactionReference = vnp_Params.get("vnp_OrderInfo");
        if (paymentId == null) {
            // Không có paymentId trong params
            productService.increaseProductQuantityList(orderRequest.getItem()); // Cộng lại Product
            response.sendRedirect("http://14.225.253.116/order-error?message=Payment information not found");
            return;
        }

        // Kiểm tra thông tin thanh toán trong Redis
        String paymentKey = "payment:" + paymentId;
        String paymentStatus = stringRedisTemplate.opsForValue().get(paymentKey);

        if (paymentStatus == null) {
            // Thông tin thanh toán không tồn tại hoặc đã hết hạn
            productService.increaseProductQuantityList(orderRequest.getItem()); // Cộng lại Product
            response.sendRedirect("http://14.225.253.116/order-error?message=Payment information not found or expired");
            return;
        }

        boolean paymentSuccess = vnPayService.handleVNPayResponse(vnp_Params);

        if (paymentSuccess) {
            // Lưu order vào cơ sở dữ liệu
            orderRequest.setTransactionReference(transactionReference);
            orderRequest.setPaymentStatus("Đã thanh toán");
            boolean success = orderService.createOrder(orderRequest);

            // Sau khi lưu order, xóa thông tin thanh toán khỏi Redis
            stringRedisTemplate.delete(paymentKey);

            // Chuyển hướng đến trang thành công
            response.sendRedirect("http://14.225.253.116/order-success");
        } else {
            productService.increaseProductQuantityList(orderRequest.getItem()); // Cộng lại Product
            // Chuyển hướng đến trang thất bại
            response.sendRedirect("http://14.225.253.116/order-error?message=Payment failed");
        }
    }


    // Create order
    @PostMapping("/create_cod")
    public ResponseEntity<TransactionStatusDTO> createOrderByCOD(@RequestBody OrderRequest orderRequest) {

        productService.reduceProductQuantityList(orderRequest.getItem()); // trừ số lượng Product
        TransactionStatusDTO transactionStatusDTO = new TransactionStatusDTO();
        // lưu order vào cơ sở dữ liệu

        String type = "COD";
        orderRequest.setOrderType(type);
        boolean success = orderService.createOrder(orderRequest);
        if (success) {
            transactionStatusDTO.setStatus("Ok");
            transactionStatusDTO.setMessage("Create Order successfully processed");
        } else {
            transactionStatusDTO.setStatus("No");
            transactionStatusDTO.setMessage("Create Order failed");
            productService.increaseProductQuantityList(orderRequest.getItem()); // Cộng lại Product
        }

        return ResponseEntity.status(success ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(transactionStatusDTO);
    }

    // Update order by id
    @PutMapping("/update/orId={id}")
    public Order updateOrder(@PathVariable("id") int orId, @RequestBody OrderRequest orderRequest) {
        return orderService.updateOrder(orId, orderRequest);
    }

    // Cancel order by id
    @PutMapping("/cancel/orId={id}")
    public String cancelOrder(@PathVariable("id") int id, @RequestBody CancelOrderRequest request) {
        return orderService.cancelOrder(id, request);
    }

    // Cancel order by id
    @PutMapping("/status")
    public String setStatusOrder(@RequestBody OrderStatusRequest request) {
        return orderService.setStatusOrder(request);
    }

    /**
     * Response to Client
     *
     */

    @GetMapping("/list")
    public List<OrderResponse> getOrdersList() {
        return orderService.getOrdersList();
    }

    //search Order by Email
    @GetMapping("/search_order/email/")
    public List<OrderResponse> searchOrderEmail(@RequestParam String email) {
        return orderService.getOrdersListEmail(email);
    }

    //search Order by phone number
    @GetMapping("/search_order/phone/")
    public List<OrderResponse> searchOrder(@RequestParam String phone) {
        return orderService.getOrdersListPhone(phone);
    }

    //search Order by id
    @GetMapping("/oId={id}")
    public OrderResponse getOrderByID(@PathVariable("id") int orderId) {
        return orderService.getOrderResponse(orderId);
    }

    @GetMapping("/code={code}")
    public OrderResponse getOrderByCode(@PathVariable("code") String code) {
        return orderService.getOrderCodeResponse(code);
    }



}
